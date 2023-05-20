package com.example.daerahindonesia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mapsDetail extends AppCompatActivity {
    String NamaSekolah, Lintang, Bujur, Bentuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detail);
        Intent intent = getIntent();
        NamaSekolah = intent.getStringExtra("NamaSekolah");
        Lintang = intent.getStringExtra("Lintang");
        Bujur = intent.getStringExtra("Bujur");
        Bentuk = intent.getStringExtra("Bentuk");
        addMarker();
    }

    private void addMarker() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("NamaSekolah", NamaSekolah);
        bundle.putSerializable("Lintang", Lintang);
        bundle.putSerializable("Bujur", Bujur);
        bundle.putSerializable("Bentuk", Bentuk);
        Fragment fragment = new Map_Fragment_Detail();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, fragment).commit();
    }

    public void back(View view) {
        finish();
    }
}