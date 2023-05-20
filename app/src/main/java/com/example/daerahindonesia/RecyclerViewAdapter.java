package com.example.daerahindonesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Map<Integer, Map<String, String>> datasekolah = new HashMap<Integer, Map<String, String>>();
    ArrayList<String> listNamaSekolah = new ArrayList<>();
    ArrayList<String> listNamaJalanSekolah = new ArrayList<>();
    ArrayList<String> listBentuk = new ArrayList<>();

    public RecyclerViewAdapter(Map<Integer, Map<String, String>> datasekolah, ArrayList<String> listNamaSekolah, ArrayList<String> listNamaJalanSekolah, ArrayList<String> listBentuk) {
        this.datasekolah = datasekolah;
        this.listNamaSekolah = listNamaSekolah;
        this.listNamaJalanSekolah = listNamaJalanSekolah;
        this.listBentuk = listBentuk;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_location, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("RecyclerViewAdapter", "onBindViewHolder: called for position " + position);
        if (listBentuk.get(position).contains("SD")) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
            holder.imageLocation.setImageTintList(colorStateList);
        } else if (listBentuk.get(position).contains("SMP")) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
            holder.imageLocation.setImageTintList(colorStateList);
        } else if (listBentuk.get(position).contains("SMA")) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.yellow));
            holder.imageLocation.setImageTintList(colorStateList);
        } else if (listBentuk.get(position).contains("SMK")) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.imageLocation.setImageTintList(colorStateList);
        }

        holder.tvNamaSekolah.setText(listNamaSekolah.get(position));
        holder.tvNamaJalan.setText(listNamaJalanSekolah.get(position));

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailSekolah.class);
                intent.putExtra("namaSekolah", listNamaSekolah.get(position));
                intent.putExtra("datasekolah", (Serializable) datasekolah);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNamaSekolah.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLocation;
        TextView tvNamaSekolah, tvNamaJalan;
        LinearLayout detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLocation = itemView.findViewById(R.id.imageLocation);
            tvNamaSekolah = itemView.findViewById(R.id.tvNamaSekolah);
            tvNamaJalan = itemView.findViewById(R.id.tvNamaJalan);
            detail = itemView.findViewById(R.id.detail);

        }
    }
}
