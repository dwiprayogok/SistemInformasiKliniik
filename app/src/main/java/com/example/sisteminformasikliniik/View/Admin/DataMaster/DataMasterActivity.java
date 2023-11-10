package com.example.sisteminformasikliniik.View.Admin.DataMaster;

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

public class DataMasterActivity extends AppCompatActivity implements DataMasterMenuAdapter.onDataMasterClicked {

    private RecyclerView rvMenuMasterData;
    private DataMasterMenuAdapter masterMenuAdapter;
    private List<DataMasterMenu> masterMenuList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_master_activity);
        Toolbar tbMaster = findViewById(R.id.toolbar);
        tbMaster.setTitle("Data Master");
        setSupportActionBar(tbMaster);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvMenuMasterData = findViewById(R.id.rvMenuDataMaster);

        masterMenuList.add(new DataMasterMenu("Data Pasien"));
        masterMenuList.add(new DataMasterMenu("Data Dokter / Bidan"));
        masterMenuList.add(new DataMasterMenu("Data Pemilik"));
        masterMenuList.add(new DataMasterMenu("Data Pelayanan"));
        masterMenuList.add(new DataMasterMenu("Data Obat"));

        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rvMenuMasterData.setLayoutManager(vertical);
        masterMenuAdapter = new DataMasterMenuAdapter(masterMenuList,this);
        rvMenuMasterData.setAdapter(masterMenuAdapter);
    }

    @Override
    public void onMenuMasterClicked(String namaMenu) {
        switch (namaMenu){
            case "Data Pasien" :
                Intent masterPasien = new Intent(getApplicationContext(), DataMasterPasienActivity.class);
                startActivity(masterPasien);
                break;
            case "Data Dokter / Bidan" :
                Intent intent2 = new Intent(getApplicationContext(),DataMasterDokterActivity.class);
                startActivity(intent2);
                break;
            case "Data Pemilik" :
                Intent intent111 = new Intent(getApplicationContext(),DataMasterPemilikActivity.class);
                startActivity(intent111);
                break;
            case "Data Pelayanan" :
                Intent intent3 = new Intent(getApplicationContext(),DataMasterPelayananActivity.class);
                startActivity(intent3);
                break;
            case "Data Obat" :
                Intent intent4 = new Intent(getApplicationContext(),DataMasterObatActivity.class);
                startActivity(intent4);
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
