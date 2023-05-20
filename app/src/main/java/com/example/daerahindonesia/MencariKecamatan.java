package com.example.daerahindonesia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
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

public class MencariKecamatan extends AppCompatActivity {
    Spinner sp_kecamatan;
    CheckBox cb_sd, cb_smp, cb_sma, cb_smk;
    String linkDataSekolah = "https://api-sekolah-indonesia.vercel.app/sekolah/sd?kab_kota=";
    String page = "&page=";
    String perpage = "&perPage=";
    Map<Integer, Map<String, String>> dataKecamatan = new HashMap<Integer, Map<String, String>>();
    ArrayList<String> kecamatanKodeList = new ArrayList<>();
    ArrayList<String> kecamatanNamaList = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    HashMap<Integer, String> checkbox = new HashMap<>();
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencari_kecamatan);
        Intent intent = getIntent();
        selected = intent.getStringExtra("selected").replace(" ", "");
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        sp_kecamatan = findViewById(R.id.sp_kecamatan);
        cb_sd = findViewById(R.id.cb_sd);
        cb_smp = findViewById(R.id.cb_smp);
        cb_sma = findViewById(R.id.cb_sma);
        cb_smk = findViewById(R.id.cb_smk);
        showData();
    }

    private void showData() {
        progressDialog.setMessage("Sedang Mencari Kecamatan..");
        progressDialog.show();
        StringRequest total_data_request = new StringRequest(Request.Method.GET,
                linkDataSekolah + selected,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int total_data = jsonObject.getInt("total_data");
                            int paginate = total_data / 1000 + 1;
                            for (int i=1; i <= paginate; i++){
                                String link = linkDataSekolah + selected + page + i + perpage + 1000;
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
                                                        Map<String, String> dataSekolahKecamatan = new HashMap<String, String>();
                                                        dataSekolahKecamatan.put("kode_kec", object.getString("kode_kec"));
                                                        dataSekolahKecamatan.put("kecamatan", object.getString("kecamatan"));
                                                        dataKecamatan.put(i, dataSekolahKecamatan);
                                                    }

                                                    for (int i = 0; i < dataKecamatan.size(); i++) {
                                                        Map<String, String> innerMap = dataKecamatan.get(i);
                                                        String KabupatenNama = innerMap.get("kecamatan");
                                                        String KabupatenKode = innerMap.get("kode_kec");
                                                        if (!kecamatanNamaList.contains(KabupatenNama)) {
                                                            kecamatanNamaList.add(KabupatenNama);
                                                            kecamatanKodeList.add(KabupatenKode);
                                                        }
                                                    }


                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, kecamatanNamaList);
                                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    sp_kecamatan.setAdapter(adapter);
                                                    progressDialog.dismiss();
                                                    sp_kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                            selected = kecamatanKodeList.get(position);
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
        if (cb_sd.isChecked()) {
            checkbox.put(0, "SD");
        } else {
            checkbox.remove(0);
        }
        if (cb_smp.isChecked()) {
            checkbox.put(1, "SMP");
        } else {
            checkbox.remove(1);
        }
        if (cb_sma.isChecked()) {
            checkbox.put(2, "SMA");
        } else {
            checkbox.remove(2);
        }
        if (cb_smk.isChecked()) {
            checkbox.put(3, "SMK");
        } else {
            checkbox.remove(3);
        }
        Intent intent = new Intent(MencariKecamatan.this, maps.class);
        intent.putExtra("selected", selected);
        intent.putExtra("checkbox", checkbox);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}