package com.oktaa.uts_oktavialaurina_17421032.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.oktaa.uts_oktavialaurina_17421032.R;
import com.oktaa.uts_oktavialaurina_17421032.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailPembeli extends AppCompatActivity {

    EditText edtKodeTv, edtMerkTv, edtTahunPembuatan, edtHargaTv;
    ImageView imgGambarTv;

    String strKodeTv, strMerkTv, strTahunPembuatan, strHargaTv, strGamabr, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);

        edtKodeTv = (EditText) findViewById(R.id.edtKodeTv);
        edtMerkTv = (EditText) findViewById(R.id.edtMerkTv);
        edtTahunPembuatan = (EditText) findViewById(R.id.edtTahunPembuatan);
        edtHargaTv = (EditText) findViewById(R.id.edtHargaTv);
        imgGambarTv= (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strKodeTv= i.getStringExtra("kodeTv");
        strMerkTv = i.getStringExtra("merkTv");
        strTahunPembuatan = i.getStringExtra("tahunPembuatan");
        strHargaTv = i.getStringExtra("hargaTv");
        strGamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodeTv.setText(strKodeTv);
        edtMerkTv.setText(strMerkTv);
        edtTahunPembuatan.setText(strTahunPembuatan);
        edtHargaTv.setText(strHargaTv);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGamabr)
                .into(imgGambarTv);
    }
}
