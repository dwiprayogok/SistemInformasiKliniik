package com.example.sisteminformasikliniik.View.Admin.Transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataMasterMenuAdapter;
import com.example.sisteminformasikliniik.Model.DataMasterMenu;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Dokter.RekamMedisActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuTransaksiActivity  extends AppCompatActivity implements DataMasterMenuAdapter.onDataMasterClicked {

    private RecyclerView rvMenuMasterData;
    private DataMasterMenuAdapter masterMenuAdapter;
    private List<DataMasterMenu> masterMenuList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_master_activity);
        Toolbar tbMaster = findViewById(R.id.toolbar);
        tbMaster.setTitle("Transaksi");
        setSupportActionBar(tbMaster);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvMenuMasterData = findViewById(R.id.rvMenuDataMaster);

        masterMenuList.add(new DataMasterMenu("Pasien Berobat"));
        masterMenuList.add(new DataMasterMenu("Pembayaran"));

        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rvMenuMasterData.setLayoutManager(vertical);
        masterMenuAdapter = new DataMasterMenuAdapter(masterMenuList,this);
        rvMenuMasterData.setAdapter(masterMenuAdapter);
    }

    @Override
    public void onMenuMasterClicked(String namaMenu) {
        switch (namaMenu){
            case "Pasien Berobat" :
                Intent intent = new Intent(getApplicationContext(), DataPasienBerobat.class);
                startActivity(intent);
                break;
            case "Pembayaran" :
                Intent intent2 = new Intent(getApplicationContext(), PembayaranActivity.class);
                startActivity(intent2);
                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
