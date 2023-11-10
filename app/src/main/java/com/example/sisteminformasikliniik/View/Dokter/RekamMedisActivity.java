package com.example.sisteminformasikliniik.View.Dokter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisteminformasikliniik.Adapter.DataMasterObatAdapter;
import com.example.sisteminformasikliniik.Adapter.RekamMedisAdapter;
import com.example.sisteminformasikliniik.Adapter.SearchObatAdapter;
import com.example.sisteminformasikliniik.Controller.RekamMedisController;
import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.MoneyTextWatcher;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.IRekamMedis;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RekamMedisActivity extends AppCompatActivity implements RekamMedisAdapter.onActionRekamMedisClicked, IRekamMedis,SearchObatAdapter.onActionObatClicked {

    private EditText edtSearchRekamMedis;
    private RecyclerView rvRekamMedis;
    private RekamMedisAdapter rekamMedisAdapter;
    private ExtendedFloatingActionButton fab;
    private ArrayList<RekamMedisEntity> dataForFilter;
    private List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();
    private List<DokterEntity> dokterEntities = new ArrayList<>();
    private List<DataPelayananEntity> pelayananEntityList = new ArrayList<>();
    private Dialog customDialog, searchObatDialog;
    private EditText edtKodeRekam,edtKodeBerobat,edtNama,
            edtUsia,edtAnamnesa,
            edtNamObat,edtTherapi,edtKeterangan;
    private Button btnSimpan,btnUpdate,btnHapus;
    private TextView tvValueTanggalPeriksa;
    private Spinner spDokter,spPelayanan;
    private RekamMedisController rekamMedisController;
    private LoadingDialog loadingDialog;
    private List<DataObatEntity> obatEntityList;
    private SearchObatAdapter searchObatAdapter;
    private String nikDokter, userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        loadingDialog = new LoadingDialog(RekamMedisActivity.this);
        rekamMedisController = new RekamMedisController(getApplicationContext());
        rekamMedisController.setDialog(loadingDialog);
        rekamMedisController.setRekamMedis(this);
        Bundle bundle = getIntent().getExtras();
        nikDokter = bundle.getString("nikDokter", "");
        userName = bundle.getString("userName", "");
        Toolbar tbRekamMedis = findViewById(R.id.toolbar);
        tbRekamMedis.setTitle("Rekam Medis");
        setSupportActionBar(tbRekamMedis);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtSearchRekamMedis = findViewById(R.id.edtSearchData);
        edtSearchRekamMedis.setHint("Cari Rekam Medis");
        rvRekamMedis = findViewById(R.id.rvData);
        fab = findViewById(R.id.fabAdd);
        fab.setVisibility(View.GONE);

        loadingDialog.show();
        rekamMedisEntityList = rekamMedisController.getALLDataRekamMedisPasien(userName);
        dokterEntities = rekamMedisController.getALLDataDokter();
        pelayananEntityList = rekamMedisController.getAllLayanan();
        obatEntityList = rekamMedisController.getALLObat();
        dataForFilter = new ArrayList<>();
        setRvRekamMedis();
        setupSearchFeature();
    }

    private void setRvRekamMedis(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvRekamMedis.setLayoutManager(vertical);
        rekamMedisAdapter = new RekamMedisAdapter(rekamMedisEntityList,dataForFilter,this);
        rvRekamMedis.setAdapter(rekamMedisAdapter);
        rekamMedisAdapter.updateDataList(rekamMedisEntityList);
    }

    private void setupSearchFeature(){
        edtSearchRekamMedis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                rekamMedisAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onItemClicked(RekamMedisEntity rekamMedisEntity, View view) {
        DialogForm(rekamMedisEntity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        refreshData(rekamMedisEntityList);
    }

    private void DialogForm(RekamMedisEntity rekamMedisEntity) {
        customDialog = new Dialog(RekamMedisActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_add_rekam_medis);
        customDialog.setCancelable(true);

        edtKodeRekam    = customDialog.findViewById(R.id.edtKodeRekam);
        edtKodeBerobat    = customDialog.findViewById(R.id.edtKodeBerobat);
        edtNama  = customDialog.findViewById(R.id.edtNama);
        edtUsia = customDialog.findViewById(R.id.edtUsia);
        tvValueTanggalPeriksa  = customDialog.findViewById(R.id.tvValueTanggalPeriksa);
        spDokter = customDialog.findViewById(R.id.spDokter);
        spPelayanan = customDialog.findViewById(R.id.spPelayanan);

        edtAnamnesa = customDialog.findViewById(R.id.edtAnamnesa);
        edtNamObat = customDialog.findViewById(R.id.edtNamObat);
        edtTherapi = customDialog.findViewById(R.id.edtTherapi);
        edtKeterangan = customDialog.findViewById(R.id.edtKeterangan);

        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogRekamMedis);
        btnHapus = customDialog.findViewById(R.id.btnHapusDialogRekamMedis);
        
        if (rekamMedisEntity != null) {
            edtKodeRekam.setEnabled(false);
            edtKodeBerobat.setEnabled(false);
            edtKodeRekam.setText(rekamMedisEntity.getKodeRekam());
            edtKodeBerobat.setText(rekamMedisEntity.getKodeBerobat());
            edtNama.setText(rekamMedisEntity.getNamaPasien());
            edtUsia.setText(rekamMedisEntity.getUmur());
            tvValueTanggalPeriksa.setText(rekamMedisEntity.getTanggalPeriksa());
            edtAnamnesa.setText(rekamMedisEntity.getAnamnesa());
            if (rekamMedisEntity.getObat().isEmpty()) {
            selectObat(edtNamObat);
            } else {
                edtNamObat.setText(rekamMedisEntity.getObat());
            }
            if (!rekamMedisEntity.getTherapi().isEmpty()) {
                edtTherapi.setText(rekamMedisEntity.getTherapi());
            }
            edtKeterangan.setText(rekamMedisEntity.getKeterangan());
            initSpinnerDokter(spDokter,rekamMedisEntity.getNamaDokter());
            initSpinnerDaftarPelayanan(spPelayanan,rekamMedisEntity.getPelayanan());
        }

        tvValueTanggalPeriksa.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(RekamMedisActivity.this, (timePicker, selectedHour, selectedMinute) ->
                    tvValueTanggalPeriksa.setText(String.format("%02d:%02d", selectedHour, selectedMinute)), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        btnUpdate.setOnClickListener(v -> {
            if (rekamMedisController.cekField(edtKodeRekam,edtKodeBerobat,edtNama,
                    edtUsia,edtAnamnesa,edtNamObat,edtTherapi,edtKeterangan))  {
                    rekamMedisController.setUpdateRekamMedis(
                        edtKodeBerobat.getText().toString(),
                        edtKodeRekam.getText().toString(),
                        edtNama.getText().toString(),
                        edtUsia.getText().toString(),
                        tvValueTanggalPeriksa.getText().toString(),
                        spDokter.getSelectedItem().toString(),
                        spPelayanan.getSelectedItem().toString(),
                        edtAnamnesa.getText().toString(),
                        edtNamObat.getText().toString(),
                        edtTherapi.getText().toString(),
                        edtKeterangan.getText().toString()
                );
            } else {
                ToastUtility.showToast(RekamMedisActivity.this,"Masih ada data yang kosong!");
            }
        });

        btnHapus.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        rekamMedisController.deleteDataRekamMedisPasien(edtKodeRekam.getText().toString());
                    })
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        });

        customDialog.show();
    }

    private void refreshData(List<RekamMedisEntity> rekamMedisEntityList){
        rekamMedisAdapter = new RekamMedisAdapter(rekamMedisEntityList,dataForFilter,this);
        rekamMedisAdapter.updateDataList(rekamMedisEntityList);
        rvRekamMedis.setAdapter(rekamMedisAdapter);
        loadingDialog.dismiss();
    }


    private void initSpinnerDokter(Spinner spDokter,String namaDokter){
        List<String> listNamaDokter = new ArrayList<>();
        for (DokterEntity entity: dokterEntities) {
            listNamaDokter.add(entity.getNamaDokter());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item,listNamaDokter); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDokter.setAdapter(spinnerArrayAdapter);

        spDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            if (!namaDokter.isEmpty()) {
                int spinnerPosition = spinnerArrayAdapter.getPosition(namaDokter);
                spDokter.setSelection(spinnerPosition);
            }

    }

    private void initSpinnerDaftarPelayanan(Spinner spPelayanan,String jenisPelayanan){
        List<String> namaPelayanan = new ArrayList<>();
        for (DataPelayananEntity entity: pelayananEntityList) {
            namaPelayanan.add(entity.getNamaPelayanan());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item,namaPelayanan); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPelayanan.setAdapter(spinnerArrayAdapter);

        spPelayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (!jenisPelayanan.isEmpty()) {
            int spinnerPosition = spinnerArrayAdapter.getPosition(jenisPelayanan);
            spPelayanan.setSelection(spinnerPosition);
        }
    }

    private void selectObat(EditText edtNamaObat) {
        edtNamaObat.setOnClickListener(v -> {
            searchObatDialog=new Dialog(RekamMedisActivity.this);
            searchObatDialog.setContentView(R.layout.dialog_searchable_spinner);
            searchObatDialog.show();
            EditText editText=searchObatDialog.findViewById(R.id.search_data);
            RecyclerView rvObat=searchObatDialog.findViewById(R.id.rvSearchData);

            final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
            rvObat.setLayoutManager(vertical);
            searchObatAdapter = new SearchObatAdapter(obatEntityList,RekamMedisActivity.this::onItemClicked);
            rvObat.setAdapter(searchObatAdapter);
            searchObatAdapter.updateDataList(obatEntityList);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchObatAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        });
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
    public void showDataDokter(List<DokterEntity> dokterEntityList) {
        dokterEntities = dokterEntityList;
    }

    @Override
    public void showDataLayanan(List<DataPelayananEntity> dataPelayananEntityList) {
        pelayananEntityList = dataPelayananEntityList;
    }

    @Override
    public void showDataObat(List<DataObatEntity> obatEntities) {
        obatEntityList = obatEntities;
    }

    @Override
    public void showAllDataRekamMedis(List<RekamMedisEntity> rekamMedisEntities) {
        rekamMedisEntityList = rekamMedisEntities;
        refreshData(rekamMedisEntityList);
    }

    @Override
    public void onSuccessUpdateDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntities) {
        customDialog.dismiss();
        rekamMedisEntityList = rekamMedisEntities;
        refreshData(rekamMedisEntityList);
    }

    @Override
    public void onSuccessDeleteDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntities) {
        customDialog.dismiss();
        rekamMedisEntityList = rekamMedisEntities;
        refreshData(rekamMedisEntityList);
    }

    @Override
    public void onFailed(String error) {
        ToastUtility.showToast(this,error);
    }

    @Override
    public void onItemClicked(DataObatEntity dataObatEntity, View view) {
        searchObatDialog.dismiss();
        edtTherapi.append(dataObatEntity.getNamaObat()+"\n");
    }
}
