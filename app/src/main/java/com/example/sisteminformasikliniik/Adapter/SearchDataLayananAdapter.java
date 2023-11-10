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

import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDataLayananAdapter extends RecyclerView.Adapter<SearchDataLayananAdapter.SearchDataLayananViewHolder> implements Filterable {

    private List<DataPelayananEntity> dataPelayananEntityList;
    private ArrayList<DataPelayananEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActioLayananClicked onActionObatClicked;

    public SearchDataLayananAdapter(List<DataPelayananEntity> dataPelayananEntityList, onActioLayananClicked onActionObatClicked) {
        this.dataPelayananEntityList = dataPelayananEntityList;
        this.onActionObatClicked = onActionObatClicked;
    }

    @NonNull
    @Override
    public SearchDataLayananAdapter.SearchDataLayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new SearchDataLayananViewHolder(view,dataPelayananEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDataLayananViewHolder holder, int position) {
        if (null != dataPelayananEntityList && dataPelayananEntityList.size() > 0) {
            holder.bind(dataPelayananEntityList,position);
        }
    }

    @Override
    public int getItemCount() {
        if (dataPelayananEntityList.size() == 0) {
            return 1;
        } else {
            return dataPelayananEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataPelayananEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public interface onActioLayananClicked {
        void onItemClicked(DataPelayananEntity dataObatEntity , View view);
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DataPelayananEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (DataPelayananEntity item : dataForFilter) {
                    if ( item.getNamaPelayanan().toLowerCase().contains(filterPattern) || item.getNamaPelayanan().contains(filterPattern) ) {
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
            dataPelayananEntityList.clear();
            dataPelayananEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public void updateDataList(List<DataPelayananEntity> data)  {
        dataPelayananEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    class SearchDataLayananViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView tvValue;
        private ImageView optional;
        private View itemView;

        public SearchDataLayananViewHolder(View itemView, int size) {
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }
        }

        public void bind(List<DataPelayananEntity> dataPelayananEntityList, int position) {
            if (dataPelayananEntityList != null && dataPelayananEntityList.size() > 0) {
                tvValue.setVisibility(View.GONE);
                optional.setVisibility(View.GONE);
                labelTv.setText(dataPelayananEntityList.get(position).getNamaPelayanan());
                itemView.setOnClickListener(v -> {
                    onActionObatClicked.onItemClicked(dataPelayananEntityList.get(position),v);
                });
            }

        }

    }
}

