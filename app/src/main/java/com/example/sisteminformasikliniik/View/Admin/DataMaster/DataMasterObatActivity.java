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

import com.example.sisteminformasikliniik.Adapter.DataMasterObatAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.MoneyTextWatcher;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterObat;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataMasterObatActivity extends AppCompatActivity implements DataMasterObatAdapter.onActionObatClicked, IDataMasterObat {
    private ExtendedFloatingActionButton fab;
    private DataMasterObatAdapter obatAdapter;
    private RecyclerView rvDataObat;
    private KlinikDatabase klinikDatabase;
    private List<DataObatEntity> obatEntityList;
    private Dialog customDialog;
    private EditText edtKodeObat,edtNamaObat,edtHargaObat,edtSearchObat;
    private Button btnSimpan, btnUpdate,btnDelete;
    private LoadingDialog loadingDialog;
    private DataMasterController dataMasterController;
    private int idObat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        dataMasterController = new DataMasterController(klinikDatabase);
        loadingDialog = new LoadingDialog(DataMasterObatActivity.this);
        dataMasterController.setiDataaMasterObat(this);
        dataMasterController.setDialog(loadingDialog);
        Toolbar tbPelayanan = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fabAdd);
        tbPelayanan.setTitle("Data Obat");
        setSupportActionBar(tbPelayanan);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        obatEntityList = dataMasterController.getALLObat();
        rvDataObat = findViewById(R.id.rvData);
        edtSearchObat =findViewById(R.id.edtSearchData);
        edtSearchObat.setHint("Cari Obat");
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        setRvDataObat();
        setupSearchFeature();
        fab.setOnClickListener(v -> {
            DialogForm(null);
        });
    }

    private void setupSearchFeature(){
        edtSearchObat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                obatAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setRvDataObat(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataObat.setLayoutManager(vertical);
        obatAdapter = new DataMasterObatAdapter(obatEntityList,this);
        rvDataObat.setAdapter(obatAdapter);
        obatAdapter.updateDataList(obatEntityList);
        loadingDialog.dismiss();
    }

    private void DialogForm(DataObatEntity dataObatEntity) {
        customDialog = new Dialog(DataMasterObatActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_add_obat);
        customDialog.setCancelable(true);

        edtKodeObat    = customDialog.findViewById(R.id.edtKodeObat);
        edtNamaObat   = customDialog.findViewById(R.id.edtNamaObat);
        edtHargaObat   = customDialog.findViewById(R.id.edtHargaObat);
        btnSimpan  = customDialog.findViewById(R.id.btnSimpanDialogObat);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogObat);
        btnDelete = customDialog.findViewById(R.id.btnHapusDialogObat);

        if (dataObatEntity != null) {
            edtKodeObat.setText(dataObatEntity.getKodeObat());
            edtNamaObat.setText(dataObatEntity.getNamaObat());
            edtHargaObat.setText(dataObatEntity.getHargaObat());
            btnSimpan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }else {
            clearField();
        }

        edtHargaObat.addTextChangedListener(new MoneyTextWatcher(edtHargaObat));

        btnSimpan.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtKodeObat,edtNamaObat,edtHargaObat)) {
                dataMasterController.setTambahObat(edtKodeObat.getText().toString(), edtNamaObat.getText().toString(),edtHargaObat.getText().toString());
            } else {
                ToastUtility.showToast(this,"Masih ada data yang kosong!");
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (dataMasterController.cekField(edtKodeObat,edtNamaObat,edtHargaObat)) {
                dataMasterController.setUpdateObat(
                        edtKodeObat.getText().toString(),
                        edtNamaObat.getText().toString(),
                        edtHargaObat.getText().toString());
            }
        });

        btnDelete.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        dataMasterController.deleteDataObat(edtKodeObat.getText().toString());
                    })
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        });
        customDialog.show();
    }

    private void clearField(){
        dataMasterController.clearField(edtKodeObat,edtNamaObat,edtHargaObat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        refreshData(obatEntityList);
    }

    private void refreshData(List<DataObatEntity> dataObatEntityList){
        obatEntityList.clear();
        obatEntityList = dataObatEntityList;
        obatAdapter = new DataMasterObatAdapter(obatEntityList,this);
        rvDataObat.setAdapter(obatAdapter);
        obatAdapter.updateDataList(obatEntityList);
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
    public void onItemClicked(DataObatEntity dataObatEntity, View view) {
        DialogForm(dataObatEntity);
    }

    @Override
    public void onSuccessSaveDataObat(List<DataObatEntity> dataObatEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dataObatEntityList);
    }

    @Override
    public void onSuccessUpdateDataObat(List<DataObatEntity> dataObatEntityList) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        refreshData(dataObatEntityList);
    }

    @Override
    public void onSuccessDeleteData(List<DataObatEntity> dataObatEntityList) {
        customDialog.dismiss();
        refreshData(dataObatEntityList);
    }

    @Override
    public void onFailed() {
        ToastUtility.showToast(DataMasterObatActivity.this,"Gagal dalam penyimpanan Data.");
    }

    @Override
    public void showData(List<DataObatEntity> dataObatEntityList) {
        refreshData(dataObatEntityList);
    }
}
