package com.example.sisteminformasikliniik.Adapter;

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
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataDetailPasienBerobatAdapter extends RecyclerView.Adapter<DataDetailPasienBerobatAdapter.DetailPasienBerobatViewHolder> implements Filterable {


    List<DataPendaftaranEntity> dataPendaftaranEntityList;
    private ArrayList<DataPendaftaranEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActionDetailPasienClicked detailPasienClicked;

    public DataDetailPasienBerobatAdapter(List<DataPendaftaranEntity> dataPendaftaranEntityList , ArrayList<DataPendaftaranEntity> dataForFilter, onActionDetailPasienClicked itemCLicked) {
        this.dataPendaftaranEntityList = dataPendaftaranEntityList;
        this.dataForFilter = dataForFilter;
        this.detailPasienClicked = itemCLicked;
    }

    @NonNull
    @Override
    public DetailPasienBerobatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new DetailPasienBerobatViewHolder(view, dataPendaftaranEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPasienBerobatViewHolder holder, int position) {
        if (null != dataPendaftaranEntityList && dataPendaftaranEntityList.size() > 0) {
            holder.bind(dataPendaftaranEntityList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (dataPendaftaranEntityList.size() == 0) {
            return 1;
        } else {
            return dataPendaftaranEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataPendaftaranEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public interface onActionDetailPasienClicked {
        void onItemClicked(DataPendaftaranEntity dataPendaftaranEntity , View view);
    }

    public void updateDataList(List<DataPendaftaranEntity> data)  {
        dataPendaftaranEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DataPendaftaranEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (DataPendaftaranEntity item : dataForFilter) {
                    if ( item.getKodeBerobat().toLowerCase().contains(filterPattern) || item.getNamaPasien().toLowerCase().contains(filterPattern)) {
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
            dataPendaftaranEntityList.clear();
            dataPendaftaranEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class DetailPasienBerobatViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView tvValue;
        private ImageView optional;
        private View itemView;

        public DetailPasienBerobatViewHolder(View itemView, int size) {
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }
        }

        public void bind(DataPendaftaranEntity dataPendaftaranEntity) {
            tvValue.setVisibility(View.GONE);
            optional.setVisibility(View.GONE);
            labelTv.setText(dataPendaftaranEntity.getKodeBerobat());
            itemView.setOnClickListener(v -> {
                detailPasienClicked.onItemClicked(dataPendaftaranEntity,v);
            });
        }
    }
}