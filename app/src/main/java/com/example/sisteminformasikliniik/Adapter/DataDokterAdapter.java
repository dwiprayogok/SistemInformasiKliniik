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

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataDokterAdapter extends RecyclerView.Adapter<DataDokterAdapter.DataDokterViewHolder> implements Filterable {

    private List<DokterEntity> dokterEntityList;
    private ArrayList<DokterEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActionDokterClicked onActionDokterClicked;

    public DataDokterAdapter(List<DokterEntity> dokterEntityList, ArrayList<DokterEntity> dataForFilter, onActionDokterClicked onActionDokterClicked) {
        this.dokterEntityList = dokterEntityList;
        this.dataForFilter = dataForFilter;
        this.onActionDokterClicked = onActionDokterClicked;
    }

    @NonNull
    @Override
    public DataDokterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_dokter, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new DataDokterViewHolder(view,dokterEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DataDokterViewHolder holder, int position) {
        if (null != dokterEntityList && dokterEntityList.size() > 0) {
            //holder.bind(dokterEntityList.get(position));
            holder.bind(dokterEntityList,position);
        }
    }

    @Override
    public int getItemCount() {
        if (dokterEntityList.size() == 0) {
            return 1;
        } else {
            return dokterEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dokterEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }


    public void updateDataList(List<DokterEntity> data)  {
        dokterEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionDokterClicked {
        void onItemClicked(DokterEntity dokterEntity , View view);
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DokterEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (DokterEntity item : dataForFilter) {
                    if ( item.getNamaDokter().toLowerCase().contains(filterPattern) || item.getNamaDokter().contains(filterPattern) ) {
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
            dokterEntityList.clear();
            dokterEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class DataDokterViewHolder extends RecyclerView.ViewHolder {
        private TextView valueNikDokter;
        private TextView valueNamaDokter;
        private TextView valueNoHpDokter;
        private TextView valueAlamatDokter;
        private ImageView overFlow;
        private View itemView;
        public DataDokterViewHolder(View itemView, int size){
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                valueNikDokter = itemView.findViewById(R.id.value_tv_nik_dokter);
                valueNamaDokter = itemView.findViewById(R.id.value_tv_nama_dokter);
                valueNoHpDokter = itemView.findViewById(R.id.value_tv_ho_hp_dokter);
                valueAlamatDokter = itemView.findViewById(R.id.value_tv_alamat_dokter);
                overFlow = itemView.findViewById(R.id.overflow);

            }
        }

        public void bind(List<DokterEntity> dokterEntityList, int position){
            if (dokterEntityList != null && dokterEntityList.size() > 0) {
                overFlow.setVisibility(View.GONE);
                valueNikDokter.setText(dokterEntityList.get(position).getNikDokter());
                valueNamaDokter.setText(dokterEntityList.get(position).getNamaDokter());
                valueNoHpDokter.setText(dokterEntityList.get(position).getNoHp());
                valueAlamatDokter.setText(dokterEntityList.get(position).getAlamat());
                itemView.setOnClickListener(v -> onActionDokterClicked.onItemClicked(dokterEntityList.get(position),v));
            }

        }

        /*public void bind(DokterEntity dataDokter){
            overFlow.setVisibility(View.GONE);
            valueNikDokter.setText(dataDokter.getNikDokter());
            valueNamaDokter.setText(dataDokter.getNamaDokter());
            valueNoHpDokter.setText(dataDokter.getNoHp());
            valueAlamatDokter.setText(dataDokter.getAlamat());
            itemView.setOnClickListener(v -> onActionDokterClicked.onItemClicked(dataDokter,v));
        }*/
    }
}

