package com.oktaa.uts_oktavialaurina_17421032.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oktaa.uts_oktavialaurina_17421032.R;
import com.oktaa.uts_oktavialaurina_17421032.model.ModelTv;
import com.oktaa.uts_oktavialaurina_17421032.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTv extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelTv> item;

    public AdapterTv(Activity activity, List<ModelTv> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_tvv, null);


        TextView merk = (TextView) convertView.findViewById(R.id.txtMerkTv);
        TextView tahunpembuatan     = (TextView) convertView.findViewById(R.id.txttahunPembuatan);
        TextView harga         = (TextView) convertView.findViewById(R.id.txtHargaTv);
        ImageView gambarTv         = (ImageView) convertView.findViewById(R.id.gambarTv);

        merk.setText(item.get(position).getMerkTv());
        tahunpembuatan.setText(item.get(position).getTahunPembuatan());
        harga.setText("Rp." + item.get(position).getHargaTv());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarTv);
        return convertView;
    }

}