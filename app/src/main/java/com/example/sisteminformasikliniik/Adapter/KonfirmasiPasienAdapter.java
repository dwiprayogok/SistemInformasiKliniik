package com.example.sisteminformasikliniik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class KonfirmasiPasienAdapter extends RecyclerView.Adapter<KonfirmasiPasienAdapter.KonfirmasiPasienViewHolder> {

    List<DataPendaftaranEntity> pendaftaranEntityList;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    public KonfirmasiPasienAdapter( List<DataPendaftaranEntity> pendaftaranEntityList) {
        this.pendaftaranEntityList = pendaftaranEntityList;
    }

    @NonNull
    @Override
    public KonfirmasiPasienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_konfirmasi_pasien, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new KonfirmasiPasienViewHolder(view,pendaftaranEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiPasienViewHolder holder, int position) {
        if (null != pendaftaranEntityList && pendaftaranEntityList.size() > 0) {
            holder.bind(pendaftaranEntityList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (pendaftaranEntityList.size() == 0) {
            return 1;
        } else {
            return pendaftaranEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (pendaftaranEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }





    class KonfirmasiPasienViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTvNamaPasien;
        public KonfirmasiPasienViewHolder(View itemView, int size){
            super(itemView);
            if (size > 0) {
                labelTvNamaPasien = itemView.findViewById(R.id.label_tv_nama_pasien);
            }
        }

        public void bind(DataPendaftaranEntity dataPendaftaranEntity){
            if (dataPendaftaranEntity.getStatus().equals("Approved")) {
                labelTvNamaPasien.setText(dataPendaftaranEntity.getNamaPasien());
            } else {
                labelTvNamaPasien.setVisibility(View.GONE);
            }

        }
    }
}

