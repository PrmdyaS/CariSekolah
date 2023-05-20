package com.example.daerahindonesia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class maps extends AppCompatActivity {

    String linkDataSekolah = "https://api-sekolah-indonesia.vercel.app/sekolah?kec=";
    String page = "&page=";
    String perpage = "&perPage=";
    Map<Integer, Map<String, String>> datasekolah = new HashMap<Integer, Map<String, String>>();
    Context context;
    ProgressDialog progressDialog;
    String selected;
    HashMap<Integer, String> checkbox;
    TextView tv_sd, tv_smp, tv_sma, tv_smk;
    ImageView iv_sd, iv_smp, iv_sma, iv_smk;
    LinearLayout ll_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        selected = intent.getStringExtra("selected").replace(" ", "");
        checkbox = (HashMap<Integer, String>) getIntent().getSerializableExtra("checkbox");
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        getData();
        setMarker();
    }

    private void setMarker() {
        ll_toolbar = findViewById(R.id.ll_toolbar);
        tv_sd = findViewById(R.id.tv_sd);
        tv_smp = findViewById(R.id.tv_smp);
        tv_sma = findViewById(R.id.tv_sma);
        tv_smk = findViewById(R.id.tv_smk);
        iv_sd = findViewById(R.id.iv_sd);
        iv_smp = findViewById(R.id.iv_smp);
        iv_sma = findViewById(R.id.iv_sma);
        iv_smk = findViewById(R.id.iv_smk);
        ll_toolbar.setVisibility(View.VISIBLE);
        if (checkbox.containsKey(0)) {
            tv_sd.setVisibility(View.VISIBLE);
            iv_sd.setVisibility(View.VISIBLE);
        }
        if (checkbox.containsKey(1)) {
            tv_smp.setVisibility(View.VISIBLE);
            iv_smp.setVisibility(View.VISIBLE);

        }
        if (checkbox.containsKey(2)) {
            tv_sma.setVisibility(View.VISIBLE);
            iv_sma.setVisibility(View.VISIBLE);
        }
        if (checkbox.containsKey(3)) {
            tv_smk.setVisibility(View.VISIBLE);
            iv_smk.setVisibility(View.VISIBLE);
        }
    }


    private void getData() {
        progressDialog.setMessage("Sedang Mencari Data Sekolah..");
        progressDialog.show();
        StringRequest total_data_request = new StringRequest(Request.Method.GET,
                linkDataSekolah + selected,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int total_data = jsonObject.getInt("total_data");
                            int paginate = total_data / 500 + 1;
                            for (int i=1; i <= paginate; i++){
                                String link = linkDataSekolah + selected + page + i + perpage + 500;
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
                                                        dataSekolah.put("npsn", object.getString("npsn"));
                                                        dataSekolah.put("sekolah", object.getString("sekolah"));
                                                        dataSekolah.put("bentuk", object.getString("bentuk"));
                                                        dataSekolah.put("alamat_jalan", object.getString("alamat_jalan"));
                                                        dataSekolah.put("propinsi", object.getString("propinsi"));
                                                        dataSekolah.put("kabupaten_kota", object.getString("kabupaten_kota"));
                                                        dataSekolah.put("kecamatan", object.getString("kecamatan"));
                                                        dataSekolah.put("lintang", object.getString("lintang"));
                                                        dataSekolah.put("bujur", object.getString("bujur"));
                                                        datasekolah.put(i, dataSekolah);
                                                    }

                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("checkbox", checkbox);
                                                    bundle.putSerializable("datasekolah", (Serializable) datasekolah);
                                                    Fragment fragment = new Map_Fragment();
                                                    fragment.setArguments(bundle);
                                                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                                                    callRecyclerView();
                                                    progressDialog.dismiss();
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

    private void callRecyclerView() {
        ArrayList<String> listFilter = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();
        ArrayList<String> listLintang = new ArrayList<>();
        ArrayList<String> listBujur = new ArrayList<>();
        ArrayList<String> listBentuk = new ArrayList<>();
        ArrayList<String> listNamaSekolah = new ArrayList<>();
        ArrayList<String> listNamaJalanSekolah = new ArrayList<>();
        if (checkbox.containsKey(0)) {
            for (int i = 0; i < datasekolah.size(); i++) {
                Map<String, String> innerMap = datasekolah.get(i);
                listFilter.clear();
                listNama.clear();
                listLintang.clear();
                listBujur.clear();
                listFilter.add(innerMap.get("bentuk"));
                listNama.add(innerMap.get("sekolah"));
                listLintang.add(innerMap.get("lintang"));
                listBujur.add(innerMap.get("bujur"));
                if (!listNama.contains("") && !listLintang.contains("") && !listBujur.contains("")) {
                    if (listFilter.contains("SD")) {
                        listBentuk.add("SD");
                        listNamaSekolah.add(innerMap.get("sekolah"));
                        listNamaJalanSekolah.add(innerMap.get("alamat_jalan"));
                    }
                }
            }
        }
        if (checkbox.containsKey(1)) {
            for (int i = 0; i < datasekolah.size(); i++) {
                Map<String, String> innerMap = datasekolah.get(i);
                listFilter.clear();
                listNama.clear();
                listLintang.clear();
                listBujur.clear();
                listFilter.add(innerMap.get("bentuk"));
                listNama.add(innerMap.get("sekolah"));
                listLintang.add(innerMap.get("lintang"));
                listBujur.add(innerMap.get("bujur"));
                if (!listNama.contains("") && !listLintang.contains("") && !listBujur.contains("")) {
                    if (listFilter.contains("SMP")) {
                        listBentuk.add("SMP");
                        listNamaSekolah.add(innerMap.get("sekolah"));
                        listNamaJalanSekolah.add(innerMap.get("alamat_jalan"));
                    }
                }
            }
        }
        if (checkbox.containsKey(2)) {
            for (int i = 0; i < datasekolah.size(); i++) {
                Map<String, String> innerMap = datasekolah.get(i);
                listFilter.clear();
                listNama.clear();
                listLintang.clear();
                listBujur.clear();
                listFilter.add(innerMap.get("bentuk"));
                listNama.add(innerMap.get("sekolah"));
                listLintang.add(innerMap.get("lintang"));
                listBujur.add(innerMap.get("bujur"));
                if (!listNama.contains("") && !listLintang.contains("") && !listBujur.contains("")) {
                    if (listFilter.contains("SMA")) {
                        listBentuk.add("SMA");
                        listNamaSekolah.add(innerMap.get("sekolah"));
                        listNamaJalanSekolah.add(innerMap.get("alamat_jalan"));
                    }
                }
            }
        }
        if (checkbox.containsKey(3)) {
            for (int i = 0; i < datasekolah.size(); i++) {
                Map<String, String> innerMap = datasekolah.get(i);
                listFilter.clear();
                listNama.clear();
                listLintang.clear();
                listBujur.clear();
                listFilter.add(innerMap.get("bentuk"));
                listNama.add(innerMap.get("sekolah"));
                listLintang.add(innerMap.get("lintang"));
                listBujur.add(innerMap.get("bujur"));
                if (!listNama.contains("") && !listLintang.contains("") && !listBujur.contains("")) {
                    if (listFilter.contains("SMK")) {
                        listBentuk.add("SMK");
                        listNamaSekolah.add(innerMap.get("sekolah"));
                        listNamaJalanSekolah.add(innerMap.get("alamat_jalan"));
                    }
                }
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.rvListSekolah);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(datasekolah, listNamaSekolah, listNamaJalanSekolah, listBentuk);
        recyclerView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }
}
