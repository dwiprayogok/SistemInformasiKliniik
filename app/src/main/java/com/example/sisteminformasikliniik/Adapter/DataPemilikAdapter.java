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
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataPemilikAdapter extends RecyclerView.Adapter<DataPemilikAdapter.DataPemilikViewHolder> implements Filterable {

    private List<DataPemilikEntity> dataPemilikEntityList;
    private ArrayList<DataPemilikEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    onActionPemilikClicked pemilikClicked;

    public DataPemilikAdapter(List<DataPemilikEntity> dataPemilikEntityList,
                              ArrayList<DataPemilikEntity> dataForFilter, onActionPemilikClicked pemilikClicked1) {
        this.dataPemilikEntityList = dataPemilikEntityList;
        this.dataForFilter = dataForFilter;
        this.pemilikClicked = pemilikClicked1;
    }

    @NonNull
    @Override
    public DataPemilikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new DataPemilikViewHolder(view,dataPemilikEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DataPemilikViewHolder holder, int position) {
        if (null != dataPemilikEntityList && dataPemilikEntityList.size() > 0) {
            //holder.bind(dataPemilikEntityList.get(position));
            holder.bind(dataPemilikEntityList,position);
        }
    }

    @Override
    public int getItemCount() {
        if (dataPemilikEntityList.size() == 0) {
            return 1;
        } else {
            return dataPemilikEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataPemilikEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public void updateDataList(List<DataPemilikEntity> data)  {
        dataPemilikEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionPemilikClicked {
        void onItemClicked(DataPemilikEntity dataPemilikEntity , View view);
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DataPemilikEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (DataPemilikEntity item : dataForFilter) {
                    if ( item.getNamaPemilik().toLowerCase().contains(filterPattern) || item.getNamaPemilik().contains(filterPattern) ) {
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
            dataPemilikEntityList.clear();
            dataPemilikEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class DataPemilikViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView tvValue;
        private ImageView optional;
        private View itemView;
        public DataPemilikViewHolder(View itemView, int size){
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }
        }

        public void bind(List<DataPemilikEntity> dataPemilikEntity, int position){
            if (dataPemilikEntity != null && dataPemilikEntity.size() > 0) {
                tvValue.setVisibility(View.GONE);
                optional.setVisibility(View.GONE);
                labelTv.setText(dataPemilikEntity.get(position).getNamaPemilik());
                itemView.setOnClickListener(v -> pemilikClicked.onItemClicked(dataPemilikEntity.get(position),v));
            }

        }
    }
}
