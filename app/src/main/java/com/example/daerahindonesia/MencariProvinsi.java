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
import java.util.HashMap;
import java.util.Map;

public class MencariProvinsi extends AppCompatActivity {
    Spinner sp_provinsi;
    String linkDataSekolah = "https://api-sekolah-indonesia.vercel.app/sekolah/smp?page=";
    String perpage = "&perPage=";
    Map<Integer, Map<String, String>> data = new HashMap<Integer, Map<String, String>>();
    ArrayList<String> provinsiKodeList = new ArrayList<>();
    ArrayList<String> provinsiNamaList = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mencari_provinsi);

        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        sp_provinsi = findViewById(R.id.sp_provinsi);
        showData();
    }

    private void showData() {
        progressDialog.setMessage("Sedang Mencari Provinsi..");
        progressDialog.show();
        StringRequest total_data_request = new StringRequest(Request.Method.GET,
                linkDataSekolah,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int total_data = jsonObject.getInt("total_data");
                            int paginate = total_data / 3000 + 1;
                            for (int i=1; i <= paginate; i++){
                                String link = linkDataSekolah + i + perpage + 3000;
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
                                                        Map<String, String> dataSekolah = new HashMap<String, String>();
                                                        dataSekolah.put("kode_prop", object.getString("kode_prop"));
                                                        dataSekolah.put("propinsi", object.getString("propinsi"));
                                                        data.put(i, dataSekolah);
                                                    }


                                                    for (int i = 0; i < data.size(); i++) {
                                                        Map<String, String> innerMap = data.get(i);
                                                        String provinsiNama = innerMap.get("propinsi");
                                                        String provinsiKode = innerMap.get("kode_prop");
                                                        if (!provinsiNamaList.contains(provinsiNama)) {
                                                            provinsiNamaList.add(provinsiNama);
                                                            provinsiKodeList.add(provinsiKode);
                                                        }
                                                    }

                                                    if (provinsiNamaList.size() == 34){
                                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinsiNamaList);
                                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        sp_provinsi.setAdapter(adapter);
                                                        progressDialog.dismiss();
                                                        sp_provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                selected = provinsiKodeList.get(position);
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });
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

    public void MencariKabupaten(View view) {
        Intent intent = new Intent(MencariProvinsi.this, MencariKabupaten.class);
        intent.putExtra("selected", selected);
        startActivity(intent);
    }
}
