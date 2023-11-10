package com.example.sisteminformasikliniik.View.Admin.KonfirmasiPasien;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DetailKonfirmasiAdapter;
import com.example.sisteminformasikliniik.Controller.KonfirmasiPasienController;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Dokter.RekamMedisActivity;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IKonfirmasiPasien;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailKonfirmasiPasienActivity extends AppCompatActivity  implements DetailKonfirmasiAdapter.onActionDetailKonfirmasiClicked, IKonfirmasiPasien {

    RecyclerView rvDataDetailKonfirmasiPasien;
    private DetailKonfirmasiAdapter detailKonfirmasiAdapter;
    private LoadingDialog loadingDialog;
    private List<DataPendaftaranEntity> pendaftaranEntityList;
    private KonfirmasiPasienController pasienController;
    private String usiaPasien= "";
    private String kodeBerobat,namaPasien,namaDokter, waktuBerobat,jenisPelayanan,nik,keluhan,tanggalBerobat;
    private int totalPasien = 0;
    private String kodeRekam;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_konfimasi_pasien);
        loadingDialog = new LoadingDialog(DetailKonfirmasiPasienActivity.this);
        pasienController = new KonfirmasiPasienController();
        pasienController.setiKonfirmasiPasien(this);
        pasienController.setDialog(loadingDialog);
        Bundle bundle = getIntent().getExtras();
        totalPasien = bundle.getInt("totalPasien", 0);
        Toolbar tabKonfirmasi = findViewById(R.id.toolbar);
        tabKonfirmasi.setTitle("Detail Konfirmasi Berobat");
        setSupportActionBar(tabKonfirmasi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvDataDetailKonfirmasiPasien = findViewById(R.id.rvDataDetailKonfirmasiPasien);
        pendaftaranEntityList = pasienController.getAllDataPasienBerobatNeedConfirmation();
        setRvDataDetailKonfirmasiPasien();
        checkKodeRekam();
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

    private void setRvDataDetailKonfirmasiPasien() {
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataDetailKonfirmasiPasien.setLayoutManager(vertical);
        detailKonfirmasiAdapter = new DetailKonfirmasiAdapter(pendaftaranEntityList,this);
        rvDataDetailKonfirmasiPasien.setAdapter(detailKonfirmasiAdapter);
        loadingDialog.dismiss();
    }

    private void refreshData(List<DataPendaftaranEntity> dataPendaftaranEntityList){
        pendaftaranEntityList = dataPendaftaranEntityList;
        detailKonfirmasiAdapter = new DetailKonfirmasiAdapter(pendaftaranEntityList,this);
        rvDataDetailKonfirmasiPasien.setAdapter(detailKonfirmasiAdapter);
        loadingDialog.dismiss();
    }

    private void checkKodeRekam() {
        if (totalPasien == 0) {
            kodeRekam = "Rekam - " + 1;
        } else if (totalPasien > 0) {
            int total = totalPasien + 1;
            kodeRekam = "Rekam - " + total;
        }
    }

    @Override
    public void onApproved(DataPendaftaranEntity dataPendaftaranEntity) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning !!")
                .setContentText("Apakah Anda Yakin untuk Menyetujui Pasien " + dataPendaftaranEntity.getNamaPasien()  )
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    saveToRekamMedis(dataPendaftaranEntity);

                })
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .show();

    }

    @Override
    public void onRefused(DataPendaftaranEntity dataPendaftaranEntity) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Gagal")
                .setContentText("Apakah Anda Yakin untuk Menolah Pasien " + dataPendaftaranEntity.getNamaPasien())
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    refusedPasien(dataPendaftaranEntity);
                })
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .show();
    }

    @Override
    public void onShowData(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia) {
        usiaPasien = usia;
        refreshData(dataPendaftaranEntityList);
    }

    private void saveToRekamMedis(DataPendaftaranEntity dataPendaftaranEntity) {
        kodeBerobat =  dataPendaftaranEntity.getKodeBerobat();
        nik = dataPendaftaranEntity.getNik();
        jenisPelayanan =  dataPendaftaranEntity.getJenisPelayanan();
        namaDokter =  dataPendaftaranEntity.getNamaDokter();
        namaPasien =  dataPendaftaranEntity.getNamaPasien();
        tanggalBerobat = dataPendaftaranEntity.getTanggalBerobat();
        waktuBerobat =  dataPendaftaranEntity.getWaktuPelayanan();
        keluhan = dataPendaftaranEntity.getKeluhan();
        pasienController.setTambahRekamMedis(kodeBerobat,kodeRekam,namaPasien,usiaPasien,tanggalBerobat,namaDokter,"",
                "",keluhan);
    }


    @Override
    public void onSuccessSaveDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("SUKSES")
                .setContentText("Pasien Telah Selesai Dikonfirmasi")
                .setConfirmText("Ya")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    approvedPasien();
                })
                .show();
    }

    @Override
    public void onRefusedDataRekamMedisPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList) {
        for (DataPendaftaranEntity entity: dataPendaftaranEntityList) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Perhatian")
                    .setContentText("Pasien atas Nama " + entity.getNamaPasien() + "Telah Di Tolak")
                    .setConfirmText("Ya")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        refreshData(dataPendaftaranEntityList);
                    })
                    .show();
        }

    }

    private void approvedPasien() {
        pasienController.setUpdatePasienApproved(kodeBerobat,nik,namaPasien,waktuBerobat,keluhan,namaDokter,jenisPelayanan,tanggalBerobat,"Approved");
    }

    private void refusedPasien(DataPendaftaranEntity dataPendaftaranEntity) {
        kodeBerobat =  dataPendaftaranEntity.getKodeBerobat();
        nik = dataPendaftaranEntity.getNik();
        jenisPelayanan =  dataPendaftaranEntity.getJenisPelayanan();
        namaDokter =  dataPendaftaranEntity.getNamaDokter();
        namaPasien =  dataPendaftaranEntity.getNamaPasien();
        tanggalBerobat = dataPendaftaranEntity.getTanggalBerobat();
        waktuBerobat =  dataPendaftaranEntity.getWaktuPelayanan();
        keluhan = dataPendaftaranEntity.getKeluhan();
        pasienController.setUpdatePasientoRefused(kodeBerobat,nik,namaPasien,waktuBerobat,keluhan,namaDokter,jenisPelayanan,tanggalBerobat,"Refused");
    }

    @Override
    public void onSuccessUpdateDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia) {
        usiaPasien = usia;
        refreshData(dataPendaftaranEntityList);

    }

    @Override
    public void onSuccessDeleteDataPasienBerobat(List<DataPendaftaranEntity> dataPendaftaranEntityList) {

    }
}
