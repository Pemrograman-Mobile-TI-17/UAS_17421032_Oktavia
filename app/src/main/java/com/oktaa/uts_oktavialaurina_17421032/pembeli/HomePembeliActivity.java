package com.oktaa.uts_oktavialaurina_17421032.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oktaa.uts_oktavialaurina_17421032.R;
import com.oktaa.uts_oktavialaurina_17421032.adapter.AdapterTv;
import com.oktaa.uts_oktavialaurina_17421032.model.ModelTv;
import com.oktaa.uts_oktavialaurina_17421032.server.BaseURL;
import com.oktaa.uts_oktavialaurina_17421032.session.PrefSetting;
import com.oktaa.uts_oktavialaurina_17421032.session.SessionManager;
import com.oktaa.uts_oktavialaurina_17421032.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeliActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterTv adapter;
    ListView list;

    ArrayList<ModelTv> newsList = new ArrayList<ModelTv>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomePembeliActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Tv");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterTv(HomePembeliActivity.this, newsList);
        list.setAdapter(adapter);
        getAllTv();

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeliActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void getAllTv() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataTv, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data tv = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelTv tv = new ModelTv();
                                    final String _id = jsonObject.getString("_id");
                                    final String merkTv = jsonObject.getString("merkTV");
                                    final String kodeTv = jsonObject.getString("kodeTv");
                                    final String hargaTv= jsonObject.getString("hargaTv");
                                    final String tahunPembuatan = jsonObject.getString("tahunPembuatan");
                                    final String gamabar = jsonObject.getString("gambar");
                                    tv.setKodeTv(kodeTv);
                                    tv.setMerkTv(merkTv);
                                    tv.setHargaTv(hargaTv);
                                    tv.setTahunPembuatan(tahunPembuatan);
                                    tv.setGambar(gamabar);
                                    tv.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(HomePembeliActivity.this, DetailPembeli.class);
                                            a.putExtra("kodeTv", newsList.get(position).getKodeTv());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("merkTv", newsList.get(position).getMerkTv());
                                            a.putExtra("hargaTv", newsList.get(position).getHargaTv());
                                            a.putExtra("tahunPembuatan", newsList.get(position).getTahunPembuatan());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(tv);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
