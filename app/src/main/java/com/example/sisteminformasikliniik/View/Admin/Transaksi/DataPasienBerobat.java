package com.example.sisteminformasikliniik.View.Admin.Transaksi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataDetailPasienBerobatAdapter;
import com.example.sisteminformasikliniik.Adapter.KartuBerobatAdapter;
import com.example.sisteminformasikliniik.Adapter.KonfirmasiPasienAdapter;
import com.example.sisteminformasikliniik.Controller.KonfirmasiPasienController;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Admin.KonfirmasiPasien.DetailKonfirmasiPasienActivity;
import com.example.sisteminformasikliniik.View.Admin.KonfirmasiPasien.KonfirmasiPasienActivity;
import com.example.sisteminformasikliniik.View.User.KartuBerobatActivity;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IKonfirmasiPasien;

import java.util.ArrayList;
import java.util.List;

public class DataPasienBerobat extends AppCompatActivity implements DataDetailPasienBerobatAdapter.onActionDetailPasienClicked, IKonfirmasiPasien {
    private RecyclerView rvDataPasienBerobat;
    private LoadingDialog loadingDialog;
    private DataDetailPasienBerobatAdapter berobatAdapter;
    private Dialog customDialog;
    private List<DataPendaftaranEntity> dataPendaftaranEntityList;
    private ArrayList<DataPendaftaranEntity> dataForFilter;
    private EditText edtSearchData;
    private KonfirmasiPasienController pasienController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pasien_berobat);
        loadingDialog = new LoadingDialog(DataPasienBerobat.this);
        pasienController = new KonfirmasiPasienController();
        pasienController.setDialog(loadingDialog);
        pasienController.setiKonfirmasiPasien(this);
        Toolbar tabKonfirmasi = findViewById(R.id.toolbar);
        tabKonfirmasi.setTitle("Data Pasien Berobat");
        setSupportActionBar(tabKonfirmasi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dataPendaftaranEntityList = new ArrayList<>();
        rvDataPasienBerobat = findViewById(R.id.rvDataPasienBerobat);
        edtSearchData = findViewById(R.id.edtSearchData);
        edtSearchData.setHint("Kode Berobat");
        dataForFilter = new ArrayList<>();
        loadingDialog.show();
        dataPendaftaranEntityList = pasienController.getAllDataPasienBerobat();
        setRvDataPasienBerobat();
        setupSearchFeature();
    }

    private void setRvDataPasienBerobat(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rvDataPasienBerobat.setLayoutManager(vertical);
        berobatAdapter = new DataDetailPasienBerobatAdapter(dataPendaftaranEntityList, dataForFilter,  this);
        rvDataPasienBerobat.setAdapter(berobatAdapter);
        berobatAdapter.updateDataList(dataPendaftaranEntityList);
        loadingDialog.dismiss();
    }

    private void setupSearchFeature(){
        edtSearchData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                berobatAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void refreshData(List<DataPendaftaranEntity> dataPendaftaranEntityList){
        berobatAdapter = new DataDetailPasienBerobatAdapter(dataPendaftaranEntityList, dataForFilter,  this);
        rvDataPasienBerobat.setAdapter(berobatAdapter);
        berobatAdapter.updateDataList(dataPendaftaranEntityList);
        loadingDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
        refreshData(dataPendaftaranEntityList);
    }

    private void DialogForm(DataPendaftaranEntity dataPendaftaranEntity) {
        loadingDialog.show();
        customDialog = new Dialog(DataPasienBerobat.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_detail_pasien_berobat);
        customDialog.setCancelable(true);

        EditText edtKodeBerobat    = customDialog.findViewById(R.id.edtKodeBerobatDialogPasienBerobat);
        EditText edtStatus   = customDialog.findViewById(R.id.edtStatusDialogPasienBerobat);
        EditText edtNoNik    = customDialog.findViewById(R.id.edtNoNIKPasienDialogPasienBerobat);
        EditText edtNamaLengkap   = customDialog.findViewById(R.id.edtNamaDialogPasienBerobat);
        TextView edtNamaLayanan   = customDialog.findViewById(R.id.edtNamaLayananDialogPasienBerobat);
        EditText edtNamaDokter  = customDialog.findViewById(R.id.edtNamaDokterLayananDialogPasienBerobat);
        TextView tvValueTanggalberobat   = customDialog.findViewById(R.id.tvValueTanggalPeriksa);
        TextView tvValueWaktuBerobat   = customDialog.findViewById(R.id.tvValueWaktuBerobat);
        EditText edtKeluhanPasien = customDialog.findViewById(R.id.edtKeluhanPasienDialogPasienBerobat);

        Button btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogPasienBerobat);
        Button btnHapus = customDialog.findViewById(R.id.btnHapusDialogPasienBerobat);

        edtKodeBerobat.setEnabled(false);
        edtStatus.setEnabled(false);
        edtNoNik.setEnabled(false);

        if (dataPendaftaranEntity != null) {
            edtKodeBerobat.setText(dataPendaftaranEntity.getKodeBerobat());
            edtStatus.setText(dataPendaftaranEntity.getStatus());
            edtNoNik.setText(dataPendaftaranEntity.getNik());
            edtNamaLengkap.setText(dataPendaftaranEntity.getNamaPasien());
            edtNamaLayanan.setText(dataPendaftaranEntity.getJenisPelayanan());
            edtNamaDokter.setText(dataPendaftaranEntity.getNamaDokter());
            tvValueTanggalberobat.setText(dataPendaftaranEntity.getTanggalBerobat());
            tvValueWaktuBerobat.setText(dataPendaftaranEntity.getWaktuPelayanan());
            edtKeluhanPasien.setText(dataPendaftaranEntity.getKeluhan());
            loadingDialog.dismiss();
        }


        btnUpdate.setOnClickListener(v -> {
            pasienController.updateDataPasienBerobat(edtKodeBerobat.getText().toString(),
                    edtStatus.getText().toString(),edtNoNik.getText().toString(),
                    edtNamaLengkap.getText().toString(),edtNamaLayanan.getText().toString(), edtNamaDokter.getText().toString(),
                    tvValueTanggalberobat.getText().toString(),tvValueWaktuBerobat.getText().toString(),edtKeluhanPasien.getText().toString());

        });

        btnHapus.setOnClickListener(v -> {
            pasienController.deleteDataPasien(edtNoNik.getText().toString());
        });

        customDialog.show();
    }

    @Override
    public void onItemClicked(DataPendaftaranEntity dataPendaftaranEntity, View view) {
        DialogForm(dataPendaftaranEntity);
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
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
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
        customDialog.dismiss();
        refreshData(dataPendaftaranEntityList);
    }

    @Override
    public void onSuccessDeleteDataPasienBerobat(List<DataPendaftaranEntity> dataPendaftaranEntityList) {
        customDialog.dismiss();
        refreshData(dataPendaftaranEntityList);
    }
}
