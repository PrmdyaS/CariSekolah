package com.example.daerahindonesia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailSekolah extends AppCompatActivity {
    TextView nama_sekolah, alamat_jalan, npsn, kecamatan, kabupaten_kota, provinsi, lintang, bujur;
    Map<Integer, Map<String, String>> datasekolah = new HashMap<Integer, Map<String, String>>();
    ArrayList<String> listNama = new ArrayList<>();
    String NamaSekolah, Lintang, Bujur, Bentuk;
    ImageView Image1, Image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sekolah);
        Image1 = findViewById(R.id.Image1);
        Image2 = findViewById(R.id.Image2);
        nama_sekolah = findViewById(R.id.nama_sekolah);
        alamat_jalan = findViewById(R.id.alamat_jalan);
        npsn = findViewById(R.id.npsn);
        kecamatan = findViewById(R.id.kecamatan);
        kabupaten_kota = findViewById(R.id.kabupaten_kota);
        provinsi = findViewById(R.id.provinsi);
        lintang = findViewById(R.id.lintang);
        bujur = findViewById(R.id.bujur);
        Intent intent = getIntent();
        datasekolah = (Map<Integer, Map<String, String>>) intent.getSerializableExtra("datasekolah");
        String namaSekolah = intent.getStringExtra("namaSekolah");
        for (int i = 0; i < datasekolah.size(); i++) {
            Map<String, String> innerMap = datasekolah.get(i);
            listNama.clear();
            listNama.add(innerMap.get("sekolah"));
            if (listNama.contains(namaSekolah)) {
                nama_sekolah.setText(innerMap.get("sekolah"));
                alamat_jalan.setText(innerMap.get("alamat_jalan"));
                npsn.setText(innerMap.get("npsn"));
                kecamatan.setText(innerMap.get("kecamatan"));
                kabupaten_kota.setText(innerMap.get("kabupaten_kota"));
                provinsi.setText(innerMap.get("propinsi"));
                lintang.setText(innerMap.get("lintang"));
                bujur.setText(innerMap.get("bujur"));
                NamaSekolah = innerMap.get("sekolah");
                Lintang = innerMap.get("lintang");
                Bujur = innerMap.get("bujur");
                Bentuk = innerMap.get("bentuk");
                if (Bentuk.contains("SD")) {
                    Image1.setImageResource(R.drawable.vector_sd);
                    Image2.setImageResource(R.drawable.vector_sd);
                } else if (Bentuk.contains("SMP")) {
                    Image1.setImageResource(R.drawable.vector_smp);
                    Image2.setImageResource(R.drawable.vector_smp);
                } else if (Bentuk.contains("SMA")) {
                    Image1.setImageResource(R.drawable.vector_sma);
                    Image2.setImageResource(R.drawable.vector_sma);
                } else if (Bentuk.contains("SMK")) {
                    Image1.setImageResource(R.drawable.vector_sma);
                    Image2.setImageResource(R.drawable.vector_sma);
                }
            }
        }
    }

    public void back(View view) {
        finish();
    }

    public void mapsDetail(View view) {
        Intent intent = new Intent(DetailSekolah.this, mapsDetail.class);
        intent.putExtra("NamaSekolah", NamaSekolah);
        intent.putExtra("Lintang", Lintang);
        intent.putExtra("Bujur", Bujur);
        intent.putExtra("Bentuk", Bentuk);
        startActivity(intent);
    }
}