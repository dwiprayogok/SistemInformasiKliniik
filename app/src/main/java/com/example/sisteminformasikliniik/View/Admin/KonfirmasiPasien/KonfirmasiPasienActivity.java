package com.example.sisteminformasikliniik.View.Admin.KonfirmasiPasien;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.KonfirmasiPasienAdapter;
import com.example.sisteminformasikliniik.Controller.KonfirmasiPasienController;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IKonfirmasiPasien;

import java.util.ArrayList;
import java.util.List;

public class KonfirmasiPasienActivity  extends AppCompatActivity implements  IKonfirmasiPasien {

    private RecyclerView rvDataKonfirmasiPasien;
    private KonfirmasiPasienAdapter pasienAdapter;
    private KlinikDatabase klinikDatabase;
    private LoadingDialog loadingDialog;
    private List<DataPendaftaranEntity> pendaftaranEntityList;
    private KonfirmasiPasienController pasienController;
    private Button btnKonfirmasiPasien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_pasien_activity);
        loadingDialog = new LoadingDialog(KonfirmasiPasienActivity.this);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        pasienController = new KonfirmasiPasienController();
        pasienController.setKlinikDatabase(klinikDatabase);
        pasienController.setiKonfirmasiPasien(this);

        Toolbar tabKonfirmasi = findViewById(R.id.toolbar);
        tabKonfirmasi.setTitle("Konfirmasi Berobat");
        setSupportActionBar(tabKonfirmasi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvDataKonfirmasiPasien = findViewById(R.id.rvDataKonfirmasiPasien);
        btnKonfirmasiPasien = findViewById(R.id.btnKonfirmasiPasien);
        btnKonfirmasiPasien.setEnabled(false);
        loadingDialog.show();
        pendaftaranEntityList = pasienController.getAllDataPasienApproved("Approved");
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        setRvDataKonfirmasiPasien();
    }

    private void setRvDataKonfirmasiPasien() {
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvDataKonfirmasiPasien.setLayoutManager(vertical);
        pasienAdapter = new KonfirmasiPasienAdapter(pendaftaranEntityList);
        rvDataKonfirmasiPasien.setAdapter(pasienAdapter);
        loadingDialog.dismiss();
    }

    private void refreshData(List<DataPendaftaranEntity> dataPendaftaranEntityList) {
        pendaftaranEntityList = dataPendaftaranEntityList;
        pasienAdapter = new KonfirmasiPasienAdapter(pendaftaranEntityList);
        rvDataKonfirmasiPasien.setAdapter(pasienAdapter);
        loadingDialog.dismiss();
        setActiveButton();
    }

    private void setActiveButton(){
        btnKonfirmasiPasien.setEnabled(true);
        btnKonfirmasiPasien.setBackground(getResources().getDrawable(R.drawable.bg_button));
        btnKonfirmasiPasien.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailKonfirmasiPasienActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("totalPasien",pendaftaranEntityList.size());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
        refreshData(pendaftaranEntityList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onShowData(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia) {
        refreshData(dataPendaftaranEntityList);
    }

    @Override
    public void onSuccessSaveDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList) {

    }


    @Override
    public void onRefusedDataRekamMedisPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList) {

    }

    @Override
    public void onSuccessUpdateDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia) {

    }

    @Override
    public void onSuccessDeleteDataPasienBerobat(List<DataPendaftaranEntity> dataPendaftaranEntityList) {

    }
}
