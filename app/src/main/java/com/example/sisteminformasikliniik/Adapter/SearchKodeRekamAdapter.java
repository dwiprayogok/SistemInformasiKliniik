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
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class SearchKodeRekamAdapter extends RecyclerView.Adapter<SearchKodeRekamAdapter.SearchKodeRekamViewHolder> implements Filterable {

    private List<RekamMedisEntity> rekamMedisEntityList;
    private ArrayList<RekamMedisEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActionKodeRekamClicked kodeRekamClicked;

    public SearchKodeRekamAdapter(List<RekamMedisEntity> rekamMedisEntityList, onActionKodeRekamClicked actionKodeRekamClicked) {
        this.rekamMedisEntityList = rekamMedisEntityList;
        this.kodeRekamClicked = actionKodeRekamClicked;
    }

    @NonNull
    @Override
    public SearchKodeRekamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new SearchKodeRekamViewHolder(view,rekamMedisEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchKodeRekamViewHolder holder, int position) {
        if (null != rekamMedisEntityList && rekamMedisEntityList.size() > 0) {
            holder.bind(rekamMedisEntityList,position);
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

    public interface onActionKodeRekamClicked {
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
                    if ( item.getKodeRekam().toLowerCase().contains(filterPattern) || item.getKodeRekam().contains(filterPattern) ) {
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


    public void updateDataList(List<RekamMedisEntity> data)  {
        rekamMedisEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    class SearchKodeRekamViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView tvValue;
        private ImageView optional;
        private View itemView;

        public SearchKodeRekamViewHolder(View itemView, int size) {
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }
        }

        public void bind(List<RekamMedisEntity> dataObatEntityList, int position) {
            if (dataObatEntityList != null && dataObatEntityList.size() > 0) {
                tvValue.setVisibility(View.GONE);
                optional.setVisibility(View.GONE);
                labelTv.setText(dataObatEntityList.get(position).getKodeRekam());
                itemView.setOnClickListener(v -> {
                    kodeRekamClicked.onItemClicked(dataObatEntityList.get(position),v);
                });
            }

        }

    }
}
