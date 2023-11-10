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
import com.example.sisteminformasikliniik.Model.KartuBerobat;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class KartuBerobatAdapter extends RecyclerView.Adapter<KartuBerobatAdapter.KartuBerobatViewHolder> implements Filterable {

    List<KartuBerobat> kartuBerobats;
    private ArrayList<KartuBerobat> dataForFilter;
    private static final int VIEW_TYPE_ACTIVITY = 1;
    private static final int VIEW_TYPE_EMPTY = 2;
    private onActionKartuClicked kartuClicked;

    public KartuBerobatAdapter(List<KartuBerobat> kartuBerobatList , ArrayList<KartuBerobat> dataForFilter,onActionKartuClicked itemCLicked) {
        this.kartuBerobats = kartuBerobatList;
        this.dataForFilter = dataForFilter;
        this.kartuClicked = itemCLicked;
    }

    @NonNull
    @Override
    public KartuBerobatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_ACTIVITY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_kartu_berobat, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_empty_item, parent, false);
        }
        return new KartuBerobatViewHolder(view,kartuBerobats.size());
    }

    @Override
    public void onBindViewHolder(@NonNull KartuBerobatViewHolder holder, int position) {
        if (null != kartuBerobats && kartuBerobats.size() > 0) {
            holder.bind(kartuBerobats.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (kartuBerobats.size() == 0) {
            return 1;
        } else {
            return kartuBerobats.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (kartuBerobats.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_ACTIVITY;
        }
    }

    public interface onActionKartuClicked {
        void onItemClicked(KartuBerobat kartuBerobat , View view);
    }

    public void updateDataList(List<KartuBerobat> data)  {
        kartuBerobats = data;
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
            ArrayList<KartuBerobat> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataForFilter);
            } else {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for (KartuBerobat item : dataForFilter) {
                    if (item.getNamaLengkapPasien().toLowerCase().trim().contains(filterPattern) || item.getNik().contains(filterPattern)) {
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
            kartuBerobats.clear();
            kartuBerobats.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class KartuBerobatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKodeKartuObat;
        private TextView valueStatusPasien;
        private View itemView;

        public KartuBerobatViewHolder(View itemView, int size) {
            super(itemView);
            this.itemView = itemView;
            if (size > 0) {
                tvKodeKartuObat = itemView.findViewById(R.id.tvKodeKartuObat);
                valueStatusPasien = itemView.findViewById(R.id.value_status_pasien);
            }
        }

        public void bind(KartuBerobat kartuBerobat) {
            tvKodeKartuObat.setText(kartuBerobat.getKodeBerobat());
            if (kartuBerobat.getStatus().equals("Approved")) {
                valueStatusPasien.setText("Diterima");
            } else if (kartuBerobat.getStatus().equals("Refused")){
                valueStatusPasien.setText("Ditolak");
            } else {
                valueStatusPasien.setText("Belum Diterima");
            }

            itemView.setOnClickListener(v -> {
                kartuClicked.onItemClicked(kartuBerobat,v);
            });
        }
    }
}
