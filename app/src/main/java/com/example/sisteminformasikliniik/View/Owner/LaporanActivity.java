package com.example.sisteminformasikliniik.View.Owner;

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

import java.util.ArrayList;
import java.util.List;

public class LaporanActivity extends AppCompatActivity implements DataMasterMenuAdapter.onDataMasterClicked {
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

        masterMenuList.add(new DataMasterMenu("Laporan Data Pasien"));
        masterMenuList.add(new DataMasterMenu("Laporan Pasien  Berobat"));
        masterMenuList.add(new DataMasterMenu("Laporan Rekam Medis"));
        masterMenuList.add(new DataMasterMenu("Laporan Pembayaran"));

        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rvMenuMasterData.setLayoutManager(vertical);
        masterMenuAdapter = new DataMasterMenuAdapter(masterMenuList,this);
        rvMenuMasterData.setAdapter(masterMenuAdapter);
    }

    @Override
    public void onMenuMasterClicked(String namaMenu) {
        switch (namaMenu){
            case "Laporan Data Pasien" :
                Intent Pasien = new Intent(getApplicationContext(), ReportDataPasienActivity.class);
                startActivity(Pasien);
                break;
            case "Laporan Pasien  Berobat" :
                Intent Berobat = new Intent(getApplicationContext(), ReportPasienBerobatActivity.class);
                startActivity(Berobat);
                break;
            case "Laporan Rekam Medis" :
                Intent Rekam = new Intent(getApplicationContext(), ReportRekamMedisActivity.class);
                startActivity(Rekam);
                break;
            case "Laporan Pembayaran" :
                Intent Pembayaran = new Intent(getApplicationContext(), ReportPembayaranActivity.class);
                startActivity(Pembayaran);
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