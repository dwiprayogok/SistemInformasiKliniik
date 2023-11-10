package com.example.sisteminformasikliniik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Admin.Transaksi.PembayaranActivity;

import java.util.ArrayList;
import java.util.List;

public class PembayaranAdapter extends RecyclerView.Adapter<PembayaranAdapter.PembayaranViewHolder> implements Filterable {

    private Context context;
    List<PembayaranEntity> pembayaranActivities;
    ArrayList<PembayaranEntity> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    onActionPembayaranClicked onActionPembayaranClicked;

    public PembayaranAdapter( List<PembayaranEntity> pembayaranActivities,ArrayList<PembayaranEntity> dataForFilter,Context context, onActionPembayaranClicked onActionPembayaranClicked) {
        this.pembayaranActivities = pembayaranActivities;
        this.dataForFilter = dataForFilter;
        this.context = context;
        this.onActionPembayaranClicked = onActionPembayaranClicked;
    }

    @NonNull
    @Override
    public PembayaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pembayaran, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new PembayaranViewHolder(view,pembayaranActivities.size());
    }

    @Override
    public void onBindViewHolder(@NonNull PembayaranViewHolder holder, int position) {
        if (null != pembayaranActivities && pembayaranActivities.size() > 0) {
            holder.bind(pembayaranActivities,position);
        }
    }

    @Override
    public int getItemCount() {
        if (pembayaranActivities.size() == 0) {
            return 1;
        } else {
            return pembayaranActivities.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (pembayaranActivities.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public void updateDataList(List<PembayaranEntity> data)  {
        pembayaranActivities = data;
        dataForFilter = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public interface onActionPembayaranClicked {
        void onEdit(PembayaranEntity pembayaranEntity );
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private Filter dataListFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PembayaranEntity> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (PembayaranEntity item : dataForFilter) {
                    if ( item.getKodeFaktur().toLowerCase().contains(filterPattern) || item.getNamaPasien().toLowerCase().contains(filterPattern) ) {
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
            pembayaranActivities.clear();
            pembayaranActivities.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class PembayaranViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPembayaran;
        private View itemView;
        public PembayaranViewHolder(View itemView, int size){
            super(itemView);
            this.itemView =itemView;
            if (size>0){
                tvPembayaran = itemView.findViewById(R.id.tvPembayaran);
            }
        }

        public void bind(List<PembayaranEntity> pembayaranEntity, int position){
            if (pembayaranEntity !=null && pembayaranEntity.size() > 0) {
                tvPembayaran.setText(pembayaranEntity.get(position).getKodeFaktur());
                itemView.setOnClickListener(v -> {
                  onActionPembayaranClicked.onEdit(pembayaranEntity.get(position));
                });
            }
        }
    }
}

