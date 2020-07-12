package com.oktaa.uts_oktavialaurina_17421032.admin;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.oktaa.uts_oktavialaurina_17421032.R;
import com.oktaa.uts_oktavialaurina_17421032.adapter.AdapterTv;
import com.oktaa.uts_oktavialaurina_17421032.model.ModelTv;
import com.oktaa.uts_oktavialaurina_17421032.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataTv extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterTv adapter;
    ListView list;

    ArrayList<ModelTv> newsList = new ArrayList<ModelTv>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tv);

        getSupportActionBar().setTitle("Data TV");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterTv(ActivityDataTv.this, newsList);
        list.setAdapter(adapter);
        getAllTv();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataTv.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
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
                                    final String merkTv = jsonObject.getString("merkTv");
                                    final String kodeTv = jsonObject.getString("kodeTv");
                                    final String hargaTv = jsonObject.getString("hargaTv");
                                    final String tahunPembuatan = jsonObject.getString("tahunPembuatan");
                                    final String gambar = jsonObject.getString("gambar");
                                    tv.setKodeTv(kodeTv);
                                    tv.setMerkTv(merkTv);
                                    tv.setHargaTv(hargaTv);
                                    tv.setTahunPembuatan(tahunPembuatan);
                                    tv.setGambar(gambar);
                                    tv.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataTv.this, HomeAdminActivity.EditTvDanHapusActivity.class);
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
