package com.example.sisteminformasikliniik.View.Admin.DataMaster;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataPemilikAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterPemilik;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataMasterPemilikActivity extends AppCompatActivity implements DataPemilikAdapter.onActionPemilikClicked, IDataMasterPemilik {
    private RecyclerView rvDataPemilik;
    private DataPemilikAdapter dataPemilikAdapter;
    private ExtendedFloatingActionButton fab;
    private KlinikDatabase klinikDatabase;
    private List<DataPemilikEntity> dataPemilikEntityList;
    private ArrayList<DataPemilikEntity> dataForFilter;
    private Dialog customDialog;
    private EditText edtUsername,edtPassword, edtNIk, edtNamaPemilik, edtNoHp, edtAlamat, edtSearchPemilik;
    private Button btnSimpan, btnHapus, btnUpdate;
    private LoadingDialog loadingDialog;
    private DataMasterController dataMasterController;
    private int idDataPemilik;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        loadingDialog = new LoadingDialog(DataMasterPemilikActivity.this);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        dataMasterController = new DataMasterController(klinikDatabase);
        dataMasterController.setiDataMasterPemilik(this);
        dataMasterController.setDialog(loadingDialog);
        Toolbar tbPemilik = findViewById(R.id.toolbar);
        tbPemilik.setTitle("Data Pemilik");
        fab = findViewById(R.id.fabAdd);
        setSupportActionBar(tbPemilik);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //dataPemilikEntityList = new ArrayList<>();
        dataPemilikEntityList = dataMasterController.getALLPemilik();
        dataForFilter = new ArrayList<>();
        rvDataPemilik = findViewById(R.id.rvData);
        edtSearchPemilik = findViewById(R.id.edtSearchData);
        edtSearchPemilik.setHint("Nama Pemilik");
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
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
        refreshData(dataPemilikEntityList);
    }

    private void setupSearchFeature(){
        edtSearchPemilik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                dataPemilikAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setRvDataDokter(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataPemilik.setLayoutManager(vertical);
        dataPemilikAdapter = new DataPemilikAdapter(dataPemilikEntityList,dataForFilter,this);
        rvDataPemilik.setAdapter(dataPemilikAdapter);
        dataPemilikAdapter.updateDataList(dataPemilikEntityList);
        loadingDialog.dismiss();
    }

    private void DialogForm(DataPemilikEntity dataPemilikEntity) {
        customDialog = new Dialog(DataMasterPemilikActivity.this);
        //customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setTitle("Tambah Pemilik");
        customDialog.setContentView(R.layout.dialog_add_pemilik);
        customDialog.setCancelable(true);

        edtUsername    = customDialog.findViewById(R.id.edtUsernameDialogPemilik);
        edtPassword    = customDialog.findViewById(R.id.edtPasswordDialogPemilik);
        edtNIk    = customDialog.findViewById(R.id.edtNIKDialogPemilik);
        edtNamaPemilik = customDialog.findViewById(R.id.edtNamaDokterDialogPemilik);
        edtNoHp  = customDialog.findViewById(R.id.edtNoHpDialogPemilik);
        edtAlamat = customDialog.findViewById(R.id.edtAlamatDialogPemilik);
        btnSimpan  = customDialog.findViewById(R.id.btnSimpanDialogPemilik);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogPemilik);
        btnHapus = customDialog.findViewById(R.id.btnHapusDialogPemilik);

        if (dataPemilikEntity != null) {
            edtUsername.setEnabled(false);
            edtPassword.setEnabled(false);
            edtUsername.setText(dataPemilikEntity.getUsername());
            edtPassword.setText(dataPemilikEntity.getPassword());
            edtNIk.setText(dataPemilikEntity.getNik());
            edtNamaPemilik.setText(dataPemilikEntity.getNamaPemilik());
            edtNoHp.setText(dataPemilikEntity.getNoHp());
            edtAlamat.setText(dataPemilikEntity.getAlamat());
            btnSimpan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnHapus.setVisibility(View.VISIBLE);

        } else {
            dataMasterController.clearField(edtUsername,edtPassword,edtNIk, edtNamaPemilik,edtNoHp,edtAlamat);
        }

        btnSimpan.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtUsername,edtPassword, edtNIk, edtNamaPemilik,edtNoHp,edtAlamat)) {
                dataMasterController.setTambahPemilik(
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString(),
                        edtNIk.getText().toString(),
                        edtNamaPemilik.getText().toString(),
                        edtNoHp.getText().toString(),
                        edtAlamat.getText().toString());
            } else {
                dataMasterController.cekField(edtNIk, edtNamaPemilik,edtNoHp,edtAlamat);
                ToastUtility.showToast(this,"Masih ada data yang kosong!");
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtNIk, edtNamaPemilik,edtNoHp,edtAlamat)) {
                dataMasterController.setUpdatePemilik(edtUsername.getText().toString(),edtPassword.getText().toString(),
                        edtNIk.getText().toString(), edtNamaPemilik.getText().toString(),
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
                        dataMasterController.deleteDataPemilik(edtNIk.getText().toString());
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

    private void refreshData(List<DataPemilikEntity> dataPemilikEntities){
        dataPemilikEntityList.clear();
        dataPemilikEntityList = dataPemilikEntities;
        dataPemilikAdapter = new DataPemilikAdapter(dataPemilikEntityList,dataForFilter,this);
        rvDataPemilik.setAdapter(dataPemilikAdapter);
        dataPemilikAdapter.updateDataList(dataPemilikEntityList);
        loadingDialog.dismiss();
    }

    @Override
    public void onSuccessDeleteData(List<DataPemilikEntity> dataPemilikEntityList) {
       customDialog.dismiss();
       refreshData(dataPemilikEntityList);
    }

    @Override
    public void onSuccessSaveData(List<DataPemilikEntity> dataPemilikEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dataPemilikEntityList);
    }

    @Override
    public void onSuccessUpdateData(List<DataPemilikEntity> dataPemilikEntityList) {
        ToastUtility.showToast(this,"Data berhasil di Update");
        customDialog.dismiss();
        refreshData(dataPemilikEntityList);
    }

    @Override
    public void showData(List<DataPemilikEntity> dataPemilikEntityList) {
        refreshData(dataPemilikEntityList);
    }

    @Override
    public void onFailed(String message) {
        ToastUtility.showToast(DataMasterPemilikActivity.this,message);
    }

    @Override
    public void onItemClicked(DataPemilikEntity dataPemilikEntity, View view) {
        DialogForm(dataPemilikEntity);
    }
}

