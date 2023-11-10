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

import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataPasienAdapter extends RecyclerView.Adapter<DataPasienAdapter.DataPasienViewHolder> implements Filterable {

    private List<UserEntity> userEntities;
    ArrayList<UserEntity> dataForFilter;
    private onActionClicked onActionClicked;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    public DataPasienAdapter(List<UserEntity> userEntityList, ArrayList<UserEntity> dataForFilter, onActionClicked onActionClicked) {
        this.userEntities = userEntityList;
        this.dataForFilter = dataForFilter;
        this.onActionClicked = onActionClicked;
    }

    @NonNull
    @Override
    public DataPasienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new DataPasienViewHolder(view,userEntities.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DataPasienViewHolder holder, int position) {
        if (null != userEntities && userEntities.size() > 0) {
            holder.bind(userEntities,position);
        }
    }

    @Override
    public int getItemCount() {
        if (userEntities.size() == 0) {
            return 1;
        } else {
            return userEntities.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userEntities.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<UserEntity> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (UserEntity item : dataForFilter) {
                    if ( item.getNama().toLowerCase().contains(filterPattern) || item.getNama().contains(filterPattern) ) {
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
            userEntities.clear();
            userEntities.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void updateDataList(List<UserEntity> data)  {
        userEntities = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionClicked {
        void onItemClicked(UserEntity userEntities , View view);
    }

    class DataPasienViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaPasien;
        private TextView tvValue;
        private ImageView optional;
        public DataPasienViewHolder(View itemView, int size){
            super(itemView);
            if (size > 0) {
                tvNamaPasien = itemView.findViewById(R.id.label_tv);
                tvValue = itemView.findViewById(R.id.value_tv);
                optional = itemView.findViewById(R.id.optional);
            }

        }

        public void bind(List<UserEntity> userEntities,int position){
            tvValue.setVisibility(View.GONE);
            optional.setVisibility(View.GONE);
            tvNamaPasien.setText(userEntities.get(position).getNama());
            itemView.setOnClickListener(v -> {
                onActionClicked.onItemClicked(userEntities.get(position),v);
            });
        }
    }
}
