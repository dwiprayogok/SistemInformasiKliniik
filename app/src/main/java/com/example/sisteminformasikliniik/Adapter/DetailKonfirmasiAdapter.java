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
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DetailKonfirmasiAdapter extends RecyclerView.Adapter<DetailKonfirmasiAdapter. DetailKonfirmasiPasienViewHolder>  {

    List<DataPendaftaranEntity> pendaftaranEntityList;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    onActionDetailKonfirmasiClicked onActionDetailKonfirmasiClicked;

    public DetailKonfirmasiAdapter( List<DataPendaftaranEntity> pendaftaranEntityList, onActionDetailKonfirmasiClicked onActionDetailKonfirmasiClicked) {
        this.pendaftaranEntityList = pendaftaranEntityList;
        this.onActionDetailKonfirmasiClicked = onActionDetailKonfirmasiClicked;
    }

    @NonNull
    @Override
    public  DetailKonfirmasiPasienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_konfirmasi_pasien, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new  DetailKonfirmasiPasienViewHolder(view,pendaftaranEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DetailKonfirmasiPasienViewHolder holder, int position) {
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

    public void updateDataList(List<DataPendaftaranEntity> data)  {
        pendaftaranEntityList = data;
        notifyDataSetChanged();
    }

    public interface onActionDetailKonfirmasiClicked {
        void onApproved(DataPendaftaranEntity dataPendaftaranEntity);
        void onRefused(DataPendaftaranEntity dataPendaftaranEntity);
    }

    class DetailKonfirmasiPasienViewHolder extends RecyclerView.ViewHolder {
        private TextView valueNamaPasien;
        private TextView valueNamaPelayanan;
        private TextView valueNamaDokter;
        private TextView valueTanggalBerobat;
        private TextView valueWaktuBerobat;
        private ImageView imgApprove;
        private ImageView imgRefused;
        public DetailKonfirmasiPasienViewHolder(View itemView, int size){
            super(itemView);
            if (size > 0) {
                valueNamaPasien = itemView.findViewById(R.id.value_tv_nama_pasien);
                valueNamaPelayanan = itemView.findViewById(R.id.value_tv_nama_pelayanan);
                valueNamaDokter = itemView.findViewById(R.id.value_tv_nama_dokter);
                valueTanggalBerobat = itemView.findViewById(R.id.value_tv_tanggal);
                valueWaktuBerobat = itemView.findViewById(R.id.value_tv_waktu_berobat);
                imgApprove = itemView.findViewById(R.id.imgApprove);
                imgRefused = itemView.findViewById(R.id.imgRefused);
            }
        }

        public void bind(DataPendaftaranEntity dataPendaftaranEntity){
            valueNamaPasien.setText(dataPendaftaranEntity.getNamaPasien());
            valueNamaPelayanan.setText(dataPendaftaranEntity.getJenisPelayanan());
            valueNamaDokter.setText(dataPendaftaranEntity.getNamaDokter());
            valueTanggalBerobat.setText(dataPendaftaranEntity.getTanggalBerobat());
            valueWaktuBerobat.setText(dataPendaftaranEntity.getWaktuPelayanan());
            imgApprove.setOnClickListener(v -> onActionDetailKonfirmasiClicked.onApproved(dataPendaftaranEntity));
            imgRefused.setOnClickListener(v -> onActionDetailKonfirmasiClicked.onRefused(dataPendaftaranEntity));

        }
    }
}
