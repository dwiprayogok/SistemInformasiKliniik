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
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataPasienAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterPasien;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataMasterPasienActivity extends AppCompatActivity implements DataPasienAdapter.onActionClicked, IDataMasterPasien {

    private RecyclerView rvDataPasien;
    private DataPasienAdapter dataPasienAdapter;
    private EditText edtSearchPasien;
    private KlinikDatabase klinikDatabase;
    private LoadingDialog loadingDialog;
    private Dialog customDialog;
    private Button btnUpdate,btnHapus;
    private DataMasterController dataMasterController;
    private List<UserEntity> entityList;
    private ArrayList<UserEntity> dataForFilter;
    private EditText edtUsername,edtPassword,edtNIK,edtNama,edtUsia,
            edtAlamat,edtStatusPekerjaan,edtNoHP;
    private RadioButton rbLaki,rbPerempuan;
    private int idUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_show_data_general);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        dataMasterController = new DataMasterController(klinikDatabase);
        loadingDialog = new LoadingDialog(DataMasterPasienActivity.this);
        dataMasterController.setDataMasterPasien(this);
        dataMasterController.setDialog(loadingDialog);
        Toolbar tbPasien = findViewById(R.id.toolbar);
        tbPasien.setTitle("Data Pasien");
        setSupportActionBar(tbPasien);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        loadingDialog.show();
        entityList = dataMasterController.getAllDataPasien();
        dataForFilter = new ArrayList<>();
        rvDataPasien = findViewById(R.id.rvData);
        edtSearchPasien = findViewById(R.id.edtSearchData);
        edtSearchPasien.setHint("Cari Pasien");
        setRvDataPasien();
        setupSearchFeature();
    }

    private void setupSearchFeature(){
        edtSearchPasien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                dataPasienAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setRvDataPasien(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataPasien.setLayoutManager(vertical);
        dataPasienAdapter = new DataPasienAdapter(entityList,dataForFilter,this);
        rvDataPasien.setAdapter(dataPasienAdapter);
        dataPasienAdapter.updateDataList(entityList);
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
        } else {
            loadingDialog.dismiss();
        }
        refreshData(entityList);
    }

    private void refreshData(List<UserEntity> entityList){
        dataPasienAdapter = new DataPasienAdapter(entityList,dataForFilter,this);
        rvDataPasien.setAdapter(dataPasienAdapter);
        dataPasienAdapter.updateDataList(entityList);
        loadingDialog.dismiss();
    }

    private void DialogForm(UserEntity userEntityList) {
        loadingDialog.show();
        customDialog = new Dialog(DataMasterPasienActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_show_data_pasien);
        customDialog.setCancelable(true);
        edtUsername = customDialog.findViewById(R.id.edtUsername);
        edtPassword = customDialog.findViewById(R.id.edtPassword);
        edtNIK = customDialog.findViewById(R.id.edtNIK);
        edtNama = customDialog.findViewById(R.id.edtNama);
        edtUsia = customDialog.findViewById(R.id.edtUsia);
        edtAlamat = customDialog.findViewById(R.id.edtAlamat);
        edtStatusPekerjaan = customDialog.findViewById(R.id.edtStatusPekerjaan);
        edtNoHP = customDialog.findViewById(R.id.edtNoHP);
        rbLaki = customDialog.findViewById(R.id.rbGenderLaki);
        rbPerempuan = customDialog.findViewById(R.id.rbGenderPerempuan);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogDataPasien);
        btnHapus = customDialog.findViewById(R.id.btnHapusDialogDataPasien);

        rbLaki.setOnClickListener(v -> {
            rbPerempuan.setChecked(false);
            rbLaki.setChecked(true);
        });

        rbPerempuan.setOnClickListener(v -> {
            rbPerempuan.setChecked(true);
            rbLaki.setChecked(false);
        });


        if (userEntityList != null) {
            edtUsername.setEnabled(false);
            edtPassword.setEnabled(false);
            edtNIK.setEnabled(false);
            edtUsername.setText(userEntityList.getUserName());
            edtPassword.setText(userEntityList.getPassword());
            edtNIK.setText(userEntityList.getNik());
            edtNama.setText(userEntityList.getNama());
            edtUsia.setText(userEntityList.getUsia());
            edtAlamat.setText(userEntityList.getAlamat());
            edtStatusPekerjaan.setText(userEntityList.getPekerjaan());
            edtNoHP.setText(userEntityList.getNoHp());
            if (userEntityList.getGender().contains("Laki - laki")) {
                rbLaki.setChecked(true);
            } else {
                rbPerempuan.setChecked(true);
            }
            loadingDialog.dismiss();
        }

        btnUpdate.setOnClickListener(v -> {
            String gender="" ;
            if (dataMasterController.cekField(edtUsername,edtPassword,edtNIK,edtNama,edtUsia,edtStatusPekerjaan,edtNoHP,edtAlamat)) {
                if (rbLaki.isChecked()) {
                    gender = rbLaki.getText().toString();
                } else {
                    gender = rbPerempuan.getText().toString();
                }

                dataMasterController.setUpdateProfile(
                        edtUsername.getText().toString(),
                        edtPassword.getText().toString(),
                        edtNIK.getText().toString(),
                        edtNama.getText().toString(),
                        gender,
                        edtUsia.getText().toString(),
                        edtAlamat.getText().toString(),
                        edtStatusPekerjaan.getText().toString(),
                        edtNoHP.getText().toString());

            }
        });

        btnHapus.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        dataMasterController.deleteDataPasien(edtNIK.getText().toString());
                    })
                    .setCancelButton("Cancel", sDialog -> sDialog.dismissWithAnimation())
                    .show();
        });

        customDialog.show();
    }

    @Override
    public void onItemClicked(UserEntity userEntities, View view) {
        DialogForm(userEntities);
    }

    @Override
    public void onDetailDataPasien(List<UserEntity> userEntityList) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        entityList = userEntityList;
        refreshData(userEntityList);
    }

    @Override
    public void onSuccessDeleteDataPasien(List<UserEntity> userEntityList) {
        customDialog.dismiss();
        refreshData(userEntityList);
    }

    @Override
    public void onSuccessUpdateDataPasien(List<UserEntity> userEntityList) {
        customDialog.dismiss();
        entityList = userEntityList;
        refreshData(userEntityList);
    }
}
