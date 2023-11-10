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
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class SearchObatAdapter extends RecyclerView.Adapter<SearchObatAdapter.SearchObatViewHolder> implements Filterable {

    private List<DataObatEntity> dataObatEntityList;
    private ArrayList<DataObatEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActionObatClicked onActionObatClicked;

    public SearchObatAdapter(List<DataObatEntity> dataObatEntityList, onActionObatClicked onActionObatClicked) {
        this.dataObatEntityList = dataObatEntityList;
        this.onActionObatClicked = onActionObatClicked;
    }

    @NonNull
    @Override
    public SearchObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new SearchObatViewHolder(view,dataObatEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchObatViewHolder holder, int position) {
        if (null != dataObatEntityList && dataObatEntityList.size() > 0) {
            //holder.bind(dataObatEntityList.get(position));
            holder.bind(dataObatEntityList,position);
        }
    }

    @Override
    public int getItemCount() {
        if (dataObatEntityList.size() == 0) {
            return 1;
        } else {
            return dataObatEntityList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataObatEntityList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public interface onActionObatClicked {
        void onItemClicked(DataObatEntity dataObatEntity , View view);
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DataObatEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (DataObatEntity item : dataForFilter) {
                    if ( item.getNamaObat().toLowerCase().contains(filterPattern) || item.getNamaObat().contains(filterPattern) ) {
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
            dataObatEntityList.clear();
            dataObatEntityList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public void updateDataList(List<DataObatEntity> data)  {
        dataObatEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    class SearchObatViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView tvValue;
        private ImageView optional;
        private View itemView;

        public SearchObatViewHolder(View itemView, int size) {
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }
        }

        public void bind(List<DataObatEntity> dataObatEntityList, int position) {
            if (dataObatEntityList != null && dataObatEntityList.size() > 0) {
                tvValue.setVisibility(View.GONE);
                optional.setVisibility(View.GONE);
                labelTv.setText(dataObatEntityList.get(position).getNamaObat());
                itemView.setOnClickListener(v -> {
                    onActionObatClicked.onItemClicked(dataObatEntityList.get(position),v);
                });
            }

        }

    }
}
