package com.example.daerahindonesia;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.ProgressDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Map_Fragment extends Fragment {

    ProgressDialog progressDialog;
    ArrayList<String> listNama = new ArrayList<>();
    ArrayList<String> listLintang = new ArrayList<>();
    ArrayList<String> listBujur = new ArrayList<>();
    ArrayList<String> listFilter = new ArrayList<>();

    ArrayList<String> listNamaSekolahSD = new ArrayList<>();
    ArrayList<String> listNamaSekolahSMP = new ArrayList<>();
    ArrayList<String> listNamaSekolahSMA = new ArrayList<>();
    ArrayList<String> listNamaSekolahSMK = new ArrayList<>();
    ArrayList<String> listLintangSD = new ArrayList<>();
    ArrayList<String> listLintangSMP = new ArrayList<>();
    ArrayList<String> listLintangSMA = new ArrayList<>();
    ArrayList<String> listLintangSMK = new ArrayList<>();
    ArrayList<String> listBujurSD = new ArrayList<>();
    ArrayList<String> listBujurSMP = new ArrayList<>();
    ArrayList<String> listBujurSMA = new ArrayList<>();
    ArrayList<String> listBujurSMK = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang Menampilkan Sekolah..");
        progressDialog.show();
        View view = inflater.inflate(R.layout.fragment_map_,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);

        Bundle bundle = getArguments();
        HashMap<Integer, String> checkbox = (HashMap<Integer, String>) bundle.getSerializable("checkbox");
        Map<Integer, Map<String, String>> datasekolah = (Map<Integer, Map<String, String>>) getArguments().getSerializable("datasekolah");

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                for (int i = 0; i < datasekolah.size(); i++) {
                    Map<String, String> innerMap = datasekolah.get(i);
                    listFilter.clear();
                    listNama.clear();
                    listLintang.clear();
                    listBujur.clear();
                    listFilter.add(innerMap.get("bentuk"));
                    listNama.add(innerMap.get("lintang"));
                    listLintang.add(innerMap.get("lintang"));
                    listBujur.add(innerMap.get("lintang"));
                    if (!listNama.contains("") && !listLintang.contains("") && !listBujur.contains("")) {
                        if (listFilter.contains("SD")) {
                            listNamaSekolahSD.add(innerMap.get("sekolah"));
                            listLintangSD.add(innerMap.get("lintang"));
                            listBujurSD.add(innerMap.get("bujur"));
                        } else if (listFilter.contains("SMP")) {
                            listNamaSekolahSMP.add(innerMap.get("sekolah"));
                            listLintangSMP.add(innerMap.get("lintang"));
                            listBujurSMP.add(innerMap.get("bujur"));
                        } else if (listFilter.contains("SMA")) {
                            listNamaSekolahSMA.add(innerMap.get("sekolah"));
                            listLintangSMA.add(innerMap.get("lintang"));
                            listBujurSMA.add(innerMap.get("bujur"));
                        } else if (listFilter.contains("SMK")) {
                            listNamaSekolahSMK.add(innerMap.get("sekolah"));
                            listLintangSMK.add(innerMap.get("lintang"));
                            listBujurSMK.add(innerMap.get("bujur"));
                        }
                    }
                }

                if (checkbox.containsKey(0)) {
                    ArrayList<MarkerOptions> markerPositionsSD = new ArrayList<>();
                    for (int i=0; i < listNamaSekolahSD.size(); i++){
                        markerPositionsSD.add(new MarkerOptions().position(new LatLng(Double.parseDouble(listLintangSD.get(i)), Double.parseDouble(listBujurSD.get(i)))).title(listNamaSekolahSD.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                    for (MarkerOptions markerOptions : markerPositionsSD) {
                        googleMap.addMarker(markerOptions);
                    }
                }
                if (checkbox.containsKey(1)) {
                    ArrayList<MarkerOptions> markerPositionsSD = new ArrayList<>();
                    ArrayList<MarkerOptions> markerPositionsSMP = new ArrayList<>();
                    for (int i=0; i < listNamaSekolahSMP.size(); i++){
                        markerPositionsSMP.add(new MarkerOptions().position(new LatLng(Double.parseDouble(listLintangSMP.get(i)), Double.parseDouble(listBujurSMP.get(i)))).title(listNamaSekolahSMP.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                    for (MarkerOptions markerOptions : markerPositionsSMP) {
                        googleMap.addMarker(markerOptions);
                    }
                }
                if (checkbox.containsKey(2)) {
                    ArrayList<MarkerOptions> markerPositionsSD = new ArrayList<>();
                    ArrayList<MarkerOptions> markerPositionsSMA = new ArrayList<>();
                    for (int i=0; i < listNamaSekolahSMA.size(); i++){
                        markerPositionsSMA.add(new MarkerOptions().position(new LatLng(Double.parseDouble(listLintangSMA.get(i)), Double.parseDouble(listBujurSMA.get(i)))).title(listNamaSekolahSMA.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    }
                    for (MarkerOptions markerOptions : markerPositionsSMA) {
                        googleMap.addMarker(markerOptions);
                    }
                }
                if (checkbox.containsKey(3)) {

                    ArrayList<MarkerOptions> markerPositionsSMK = new ArrayList<>();
                    for (int i=0; i < listNamaSekolahSMK.size(); i++){
                        markerPositionsSMK.add(new MarkerOptions().position(new LatLng(Double.parseDouble(listLintangSMK.get(i)), Double.parseDouble(listBujurSMK.get(i)))).title(listNamaSekolahSMK.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                    for (MarkerOptions markerOptions : markerPositionsSMK) {
                        googleMap.addMarker(markerOptions);
                    }
                }

                if (checkbox.containsKey(0)) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(listLintangSD.get(0)), Double.parseDouble(listBujurSD.get(0))), 14));
                } else if (checkbox.containsKey(1)) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(listLintangSMP.get(0)), Double.parseDouble(listBujurSMP.get(0))), 14));
                } else if (checkbox.containsKey(2)) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(listLintangSMA.get(0)), Double.parseDouble(listBujurSMA.get(0))), 14));
                } else if (checkbox.containsKey(3)) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(listLintangSMK.get(0)), Double.parseDouble(listBujurSMK.get(0))), 14));
                }

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                progressDialog.dismiss();
            }
        });
        return view;
    }
}