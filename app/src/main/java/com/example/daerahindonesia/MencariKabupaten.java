package com.example.daerahindonesia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MencariKabupaten extends AppCompatActivity {
    Spinner sp_kabupaten;
    String linkDataSekolah = "https://api-sekolah-indonesia.vercel.app/sekolah/sd?provinsi=";
    String page = "&page=";
    String perpage = "&perPage=";
    Map<Integer, Map<String, String>> dataKabupaten = new HashMap<Integer, Map<String, String>>();
    ArrayList<String> kabupatenKodeList = new ArrayList<>();
    ArrayList<String> kabupatenNamaList = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencari_kabupaten);
        Intent intent = getIntent();
        selected = intent.getStringExtra("selected").replace(" ", "");
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        sp_kabupaten = findViewById(R.id.sp_kabupaten);
        showData();
    }

    private void showData() {
        progressDialog.setMessage("Sedang Mencari Kabupaten..");
        progressDialog.show();
        StringRequest total_data_request = new StringRequest(Request.Method.GET,
                linkDataSekolah + selected,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int total_data = jsonObject.getInt("total_data");
                            int paginate = total_data / 3000 + 1;
                            for (int i=1; i <= paginate; i++){
                                String link = linkDataSekolah + selected + page + i + perpage + 3000;
                                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                        link,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    JSONArray jsonArray = jsonObject.getJSONArray("dataSekolah");
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject object = jsonArray.getJSONObject(i);
                                                        Map<String, String> dataSekolahKabupaten = new HashMap<String, String>();
                                                        dataSekolahKabupaten.put("kode_kab_kota", object.getString("kode_kab_kota"));
                                                        dataSekolahKabupaten.put("kabupaten_kota", object.getString("kabupaten_kota"));
                                                        dataKabupaten.put(i, dataSekolahKabupaten);
                                                    }

                                                    for (int i = 0; i < dataKabupaten.size(); i++) {
                                                        Map<String, String> innerMap = dataKabupaten.get(i);
                                                        String KabupatenNama = innerMap.get("kabupaten_kota");
                                                        String KabupatenKode = innerMap.get("kode_kab_kota");
                                                        if (!kabupatenNamaList.contains(KabupatenNama)) {
                                                            kabupatenNamaList.add(KabupatenNama);
                                                            kabupatenKodeList.add(KabupatenKode);
                                                        }
                                                    }


                                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, kabupatenNamaList);
                                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        sp_kabupaten.setAdapter(adapter);
                                                        progressDialog.dismiss();
                                                        sp_kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                selected = kabupatenKodeList.get(position);
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(total_data_request);
    }

    public void MencariKecamatan(View view) {
        Intent intent = new Intent(MencariKabupaten.this, MencariKecamatan.class);
        intent.putExtra("selected", selected);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}