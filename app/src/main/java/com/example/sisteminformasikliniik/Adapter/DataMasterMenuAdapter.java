package com.example.sisteminformasikliniik.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Model.DataMasterMenu;
import com.example.sisteminformasikliniik.R;

import java.util.ArrayList;
import java.util.List;

public class DataMasterMenuAdapter extends RecyclerView.Adapter<DataMasterMenuAdapter.DataMasterViewHolder> {

    List<DataMasterMenu> masterMenuList;
    onDataMasterClicked onDataMasterClicked;
    public DataMasterMenuAdapter( List<DataMasterMenu> masterMenuList,onDataMasterClicked onDataMasterClicked) {
        this.masterMenuList = masterMenuList;
        this.onDataMasterClicked = onDataMasterClicked;
    }

    @NonNull
    @Override
    public DataMasterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_admin,parent,false);
        return new DataMasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataMasterViewHolder holder, int position) {
        if (null != masterMenuList && masterMenuList.size() > 0) {
            holder.bind(masterMenuList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (null != masterMenuList) {
            return masterMenuList.size();
        } else {
            return 1;
        }
    }

    public interface onDataMasterClicked{
        void onMenuMasterClicked(String namaMenu);
    }

    class DataMasterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaMenuDataMaster;
        public DataMasterViewHolder(View itemView){
            super(itemView);
            tvNamaMenuDataMaster = itemView.findViewById(R.id.label_nama_menu_data_master);
        }

        public void bind(DataMasterMenu dataMasterMenu){
            tvNamaMenuDataMaster.setText(dataMasterMenu.getNamaMenu());
            itemView.setOnClickListener(v -> {
                onDataMasterClicked.onMenuMasterClicked(dataMasterMenu.getNamaMenu());
            });
        }
    }
}
