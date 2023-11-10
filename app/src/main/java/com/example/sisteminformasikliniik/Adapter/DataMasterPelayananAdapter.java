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
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataMasterPelayananAdapter extends RecyclerView.Adapter<DataMasterPelayananAdapter.DataMasterViewHolder> implements Filterable {

    private Context context;
    List<DataPelayananEntity> dataPelayananEntityList;
    ArrayList<DataPelayananEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    onActionPelayananClicked onActionPelayananClicked;
    public DataMasterPelayananAdapter(List<DataPelayananEntity> dataPelayananEntityList,ArrayList<DataPelayananEntity> dataForFilter , Context context,onActionPelayananClicked onActionPelayananClicked) {
        this.dataPelayananEntityList = dataPelayananEntityList;
        this.dataForFilter = dataForFilter;
        this.context = context;
        this.onActionPelayananClicked = onActionPelayananClicked;
    }

    @NonNull
    @Override
    public DataMasterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_default, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new DataMasterViewHolder(view,dataPelayananEntityList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull DataMasterViewHolder holder, int position) {
        if (null != dataPelayananEntityList && dataPelayananEntityList.size() > 0) {
            //holder.bind(dataPelayananEntityList.get(position));
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

    public void updateDataList(List<DataPelayananEntity> data)  {
        dataPelayananEntityList = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionPelayananClicked {
        void onItemClicked(DataPelayananEntity dataPelayananEntity , View view);
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

    class DataMasterViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTv;
        private TextView valueTv;
        private ImageView optional;
        private View view;
        public DataMasterViewHolder(View itemView,int size) {
            super(itemView);
            this.view = itemView ;
            if (size > 0) {
                labelTv = itemView.findViewById(R.id.label_tv);
                valueTv = itemView.findViewById(R.id.value_tv);
                optional= itemView.findViewById(R.id.optional);
            }
        }
        public void bind(List<DataPelayananEntity> dataPelayananEntity,int position) {
            if (dataPelayananEntity != null && dataPelayananEntity.size() > 0) {
                valueTv.setVisibility(View.GONE);
                optional.setVisibility(View.GONE);
                labelTv.setText(dataPelayananEntity.get(position).getNamaPelayanan());
                itemView.setOnClickListener(v -> onActionPelayananClicked.onItemClicked(dataPelayananEntity.get(position),v));
            }
        }
    }
}

