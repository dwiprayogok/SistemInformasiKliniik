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

import com.example.sisteminformasikliniik.Adapter.DataMasterPelayananAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.MoneyTextWatcher;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterLayanan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataMasterPelayananActivity extends AppCompatActivity implements DataMasterPelayananAdapter.onActionPelayananClicked, IDataMasterLayanan {
    private ExtendedFloatingActionButton fab;
    private DataMasterPelayananAdapter masterAdapter;
    private RecyclerView rvDataPelayanan;
    private KlinikDatabase klinikDatabase;
    private List<DataPelayananEntity> pelayananEntities;
    private Dialog customDialog;
    private EditText edtID,edtNamaPelayanan,edtSearchPelayanan,edtBiayaLayanan;
    private Button btnSimpan,btnUpdate,btnDelete;
    private int idPelayanan;
    private ArrayList<DataPelayananEntity> dataForFilter;
    private LoadingDialog loadingDialog;
    private DataMasterController dataMasterController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        loadingDialog = new LoadingDialog(DataMasterPelayananActivity.this);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        dataMasterController = new DataMasterController(klinikDatabase);
        dataMasterController.setiDataMasterLayanan(this);
        dataMasterController.setDialog(loadingDialog);
        Toolbar tbPelayanan = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fabAdd);
        tbPelayanan.setTitle("Data Pelayanan");
        setSupportActionBar(tbPelayanan);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //pelayananEntities = new ArrayList<>();
        pelayananEntities = dataMasterController.getAllLayanan();
        dataForFilter = new ArrayList<>();
        rvDataPelayanan = findViewById(R.id.rvData);
        edtSearchPelayanan = findViewById(R.id.edtSearchData);
        edtSearchPelayanan.setHint("Cari Pelayanan");
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        setRvDataPelayanan();
        setupSearchFeature();
        fab.setOnClickListener(v -> {
         DialogForm(null);
        });

    }

    private void setupSearchFeature(){
        edtSearchPelayanan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                masterAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setRvDataPelayanan(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataPelayanan.setLayoutManager(vertical);
        masterAdapter = new DataMasterPelayananAdapter(pelayananEntities, dataForFilter,getApplicationContext(),this);
        rvDataPelayanan.setAdapter(masterAdapter);
        masterAdapter.updateDataList(pelayananEntities);
        loadingDialog.dismiss();
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
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        refreshData(pelayananEntities);
    }

    private void refreshData(List<DataPelayananEntity> dataPelayananEntityList){
        pelayananEntities.clear();
        pelayananEntities = dataPelayananEntityList;
        masterAdapter = new DataMasterPelayananAdapter(pelayananEntities,dataForFilter,getApplicationContext(),this);
        rvDataPelayanan.setAdapter(masterAdapter);
        masterAdapter.updateDataList(pelayananEntities);
        loadingDialog.dismiss();
    }

    private void DialogForm(DataPelayananEntity dataPelayananEntity) {
        customDialog = new Dialog(DataMasterPelayananActivity.this);
        //customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setTitle("Tambah Layanan");
        customDialog.setContentView(R.layout.dialog_add_pelayanan);
        customDialog.setCancelable(true);
        edtID    = customDialog.findViewById(R.id.edtIdPelayanan);
        edtNamaPelayanan   = customDialog.findViewById(R.id.edtNamaPelayanan);
        edtBiayaLayanan   = customDialog.findViewById(R.id.edtBiayaPelayanan);
        btnSimpan  = customDialog.findViewById(R.id.btnSimpanDialogPelayanan);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogLayanan);
        btnDelete = customDialog.findViewById(R.id.btnHapusDialogLayanan);

        edtBiayaLayanan.addTextChangedListener(new MoneyTextWatcher(edtBiayaLayanan));

        if (dataPelayananEntity != null) {
            edtID.setText(dataPelayananEntity.getKodePelayanan());
            edtNamaPelayanan.setText(dataPelayananEntity.getNamaPelayanan());
            edtBiayaLayanan.setText(dataPelayananEntity.getBiayaPelayanan());
            btnSimpan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            dataMasterController.clearField(edtID,edtNamaPelayanan);
        }

        btnSimpan.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtID,edtNamaPelayanan,edtBiayaLayanan)) {
                dataMasterController.setTambahLayanan(edtID.getText().toString(),edtNamaPelayanan.getText().toString(),edtBiayaLayanan.getText().toString());
            }
        });
        btnUpdate.setOnClickListener(v -> {
                if (dataMasterController.cekField(edtID,edtNamaPelayanan,edtBiayaLayanan)) {
                    dataMasterController.setupdateLayanan(edtID.getText().toString(),edtNamaPelayanan.getText().toString(),edtBiayaLayanan.getText().toString());
                }
        });

        btnDelete.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        dataMasterController.deleteDataLayanan(edtID.getText().toString());
                    })
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        });
        customDialog.show();
    }

    @Override
    public void onFailed() {
        ToastUtility.showToast(DataMasterPelayananActivity.this,"Gagal dalam penyimpanan Data.");
    }

    @Override
    public void showData(List<DataPelayananEntity> dataPelayananEntityList) {
        refreshData(dataPelayananEntityList);
    }

    @Override
    public void onSuccessDeleteData(List<DataPelayananEntity> dataPelayananEntityList) {
        customDialog.dismiss();
        refreshData(dataPelayananEntityList);
    }

    @Override
    public void onSuccessSaveData(List<DataPelayananEntity> dataPelayananEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dataPelayananEntityList);
    }

    @Override
    public void onSuccessUpdateData(List<DataPelayananEntity> dataPelayananEntityList) {
        ToastUtility.showToast(this,"Data berhasil di Update");
        customDialog.dismiss();
        refreshData(dataPelayananEntityList);
    }

    @Override
    public void onItemClicked(DataPelayananEntity dataPelayananEntity, View view) {
        DialogForm(dataPelayananEntity);
    }
}
