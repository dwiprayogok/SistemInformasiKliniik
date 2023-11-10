package com.example.sisteminformasikliniik.View.Admin.DataMaster;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataDokterAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterDokter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataMasterDokterActivity extends AppCompatActivity implements DataDokterAdapter.onActionDokterClicked, IDataMasterDokter {
    private RecyclerView rvDataDokter;
    private DataDokterAdapter dokterAdapter;
    private ExtendedFloatingActionButton fab;
    private KlinikDatabase klinikDatabase;
    private List<DokterEntity> listDokter;
    private ArrayList<DokterEntity> dataForFilter;
    private Dialog customDialog;
    private EditText edtUsername,edtPassword, edtNIk, edtNamaDokter, edtNoHp, edtAlamat,edtSearchDokter;
    private Button btnSimpan, btnHapus, btnUpdate;
    private LoadingDialog loadingDialog;
    private DataMasterController dataMasterController;
    private int idDataDokter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        loadingDialog = new LoadingDialog(DataMasterDokterActivity.this);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        dataMasterController = new DataMasterController(klinikDatabase);
        dataMasterController.setiDataMasterDokter(this);
        dataMasterController.setDialog(loadingDialog);

        Toolbar tbDokter = findViewById(R.id.toolbar);
        tbDokter.setTitle("Data Dokter");
        fab = findViewById(R.id.fabAdd);
        setSupportActionBar(tbDokter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listDokter = dataMasterController.getALLDataDokter();
        dataForFilter = new ArrayList<>();
        rvDataDokter = findViewById(R.id.rvData);
        edtSearchDokter = findViewById(R.id.edtSearchData);
        edtSearchDokter.setHint("Cari Dokter");

        setRvDataDokter();
        setupSearchFeature();
        fab.setOnClickListener(v -> {
            DialogForm(null);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        refreshData(listDokter);
    }

    private void setupSearchFeature(){
        edtSearchDokter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                dokterAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setRvDataDokter(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataDokter.setLayoutManager(vertical);
        dokterAdapter = new DataDokterAdapter(listDokter,dataForFilter,this);
        rvDataDokter.setAdapter(dokterAdapter);
        dokterAdapter.updateDataList(listDokter);
    }

    private void DialogForm(DokterEntity dokterEntity) {
        customDialog = new Dialog(DataMasterDokterActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_add_dokter);
        customDialog.setCancelable(true);

        edtUsername    = customDialog.findViewById(R.id.edtUsernameDialogDokter);
        edtPassword    = customDialog.findViewById(R.id.edtPasswordDialogDokter);
        edtNIk    = customDialog.findViewById(R.id.edtNIKDialogDokter);
        edtNamaDokter  = customDialog.findViewById(R.id.edtNamaDokterDialogDokter);
        edtNoHp  = customDialog.findViewById(R.id.edtNoHpDialogDokter);
        edtAlamat = customDialog.findViewById(R.id.edtAlamatDialogDokter);
        btnSimpan  = customDialog.findViewById(R.id.btnSimpanDialogDokter);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogDokter);
        btnHapus = customDialog.findViewById(R.id.btnHapusDialogDokter);

        if (dokterEntity != null) {
            //idDataDokter = dokterEntity.getId();
            edtUsername.setEnabled(false);
            edtPassword.setEnabled(false);
            edtUsername.setText(dokterEntity.getUsername());
            edtPassword.setText(dokterEntity.getPassword());
            edtNIk.setText(dokterEntity.getNikDokter());
            edtNamaDokter.setText(dokterEntity.getNamaDokter());
            edtNoHp.setText(dokterEntity.getNoHp());
            edtAlamat.setText(dokterEntity.getAlamat());
            btnSimpan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnHapus.setVisibility(View.VISIBLE);

        } else {
            dataMasterController.clearField(edtUsername,edtPassword,edtNIk,edtNamaDokter,edtNoHp,edtAlamat);
        }

        btnSimpan.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtUsername,edtPassword, edtNIk,edtNamaDokter,edtNoHp,edtAlamat)) {
                dataMasterController.setTambahDokter(edtUsername.getText().toString(),edtPassword.getText().toString(),
                        edtNIk.getText().toString(),edtNamaDokter.getText().toString(), edtNoHp.getText().toString(),edtAlamat.getText().toString());
            } else {
                dataMasterController.cekField(edtNIk,edtNamaDokter,edtNoHp,edtAlamat);
                ToastUtility.showToast(this,"Masih ada data yang kosong!");
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtNIk,edtNamaDokter,edtNoHp,edtAlamat)) {
                dataMasterController.setUpdateDokter(edtUsername.getText().toString(),edtPassword.getText().toString(), edtNIk.getText().toString(),edtNamaDokter.getText().toString(),
                        edtNoHp.getText().toString(),edtAlamat.getText().toString());
            }
        });

        btnHapus.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        dataMasterController.deleteDataDokter(edtNIk.getText().toString());
                    })
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        });

       customDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshData(List<DokterEntity> dokterEntityList){
        listDokter.clear();
        listDokter = dokterEntityList;
        dokterAdapter = new DataDokterAdapter(listDokter,dataForFilter,this);
        rvDataDokter.setAdapter(dokterAdapter);
        dokterAdapter.updateDataList(listDokter);
    }

    @Override
    public void onSuccessSaveData(List<DokterEntity> dokterEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dokterEntityList);
    }

    @Override
    public void onSuccessUpdateData(List<DokterEntity> dokterEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dokterEntityList);
    }

    @Override
    public void onFailed() {
        ToastUtility.showToast(DataMasterDokterActivity.this,"Gagal dalam penyimpanan Data.");

    }

    @Override
    public void showData(List<DokterEntity> dokterEntityList) {
        refreshData(dokterEntityList);
    }

    @Override
    public void onSuccessDeleteData(List<DokterEntity> dokterEntityList) {
        customDialog.dismiss();
        refreshData(dokterEntityList);
    }


    @Override
    public void onItemClicked(DokterEntity dokterEntity, View view) {
        DialogForm(dokterEntity);
    }
}
