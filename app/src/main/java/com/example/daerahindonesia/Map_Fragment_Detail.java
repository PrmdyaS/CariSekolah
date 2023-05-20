package com.example.daerahindonesia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment_Detail extends Fragment {
    String NamaSekolah, Bentuk, Lintang, Bujur;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_detail,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP_Detail);

        Bundle bundle = getArguments();
        NamaSekolah = String.valueOf(bundle.getSerializable("NamaSekolah"));
        Lintang = String.valueOf(bundle.getSerializable("Lintang"));
        Bujur = String.valueOf(bundle.getSerializable("Bujur"));
        Bentuk = String.valueOf(bundle.getSerializable("Bentuk"));

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng location = new LatLng(Double.parseDouble(Lintang), Double.parseDouble(Bujur));
                if (Bentuk.contains("SD")) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location)
                            .title(NamaSekolah)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(markerOptions);
                } else if (Bentuk.contains("SMP")) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location)
                            .title(NamaSekolah)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    googleMap.addMarker(markerOptions);
                } else if (Bentuk.contains("SMA")) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location)
                            .title(NamaSekolah)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    googleMap.addMarker(markerOptions);
                } else if (Bentuk.contains("SMK")) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location)
                            .title(NamaSekolah)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    googleMap.addMarker(markerOptions);
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        return view;
    }
}