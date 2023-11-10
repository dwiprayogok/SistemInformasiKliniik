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

import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class RekamMedisAdapter extends RecyclerView.Adapter<RekamMedisAdapter.RekamMedisViewHolder> implements Filterable {

    List<RekamMedisEntity> rekamMedisEntityList;
    ArrayList<RekamMedisEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    onActionRekamMedisClicked onActionRekamMedisClicked;

    public RekamMedisAdapter(List<RekamMedisEntity> rekamMedisEntityList,ArrayList<RekamMedisEntity> dataForFilter, onActionRekamMedisClicked onActionRekamMedisClicked) {
        this.rekamMedisEntityList = rekamMedisEntityList;
        this.dataForFilter = dataForFilter;
        this.onActionRekamMedisClicked = onActionRekamMedisClicked;
    }

    @NonNull
    @Override
    public RekamMedisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rekam_medis, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new RekamMedisViewHolder(view,rekamMedisEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RekamMedisViewHolder holder, int position) {
        if (null != rekamMedisEntityList && rekamMedisEntityList.size() > 0) {
            holder.bind(rekamMedisEntityList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (rekamMedisEntityList.size() == 0) {
            return 1;
        } else {
            return rekamMedisEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (rekamMedisEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public void updateDataList(List<RekamMedisEntity> data)  {
        rekamMedisEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionRekamMedisClicked {
        void onItemClicked(RekamMedisEntity rekamMedisEntity , View view);
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<RekamMedisEntity> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (RekamMedisEntity item : dataForFilter) {
                    if ( item.getNamaPasien().toLowerCase().contains(filterPattern) || item.getNamaPasien().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            rekamMedisEntityList.clear();
            rekamMedisEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class RekamMedisViewHolder extends RecyclerView.ViewHolder {
        private TextView valueNamaPasien;
        private View itemView;
        public RekamMedisViewHolder(View itemView, int size){
            super(itemView);
            this.itemView = itemView;
            if (size>0){
                valueNamaPasien = itemView.findViewById(R.id.value_nama_pasien);
            }

        }

        public void bind(RekamMedisEntity rekamMedisEntity){
            valueNamaPasien.setText(rekamMedisEntity.getNamaPasien());
            itemView.setOnClickListener(v -> onActionRekamMedisClicked.onItemClicked(rekamMedisEntity,v));
        }
    }
}

