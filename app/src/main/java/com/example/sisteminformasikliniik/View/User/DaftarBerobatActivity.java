package com.example.sisteminformasikliniik.View.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Controller.UserController;
import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.User.IDaftarPelayanan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DaftarBerobatActivity extends AppCompatActivity implements IDaftarPelayanan {

    private EditText edtKodeBerobat,edtNIK,edtKeluhan;
    private Spinner spDokter,spDaftarPelayanan;
    private EditText tvWaktuBerobat,tvValueTanggalBerobat;
    private Button btnDaftar;
    private LoadingDialog loadingDialog;
    private List<DokterEntity> dokterEntityList;
    private List<DataPelayananEntity> pelayananEntityList;
    private List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    private  int noPelayanan = 0;
    private  DatePickerDialog datePickerDialog;
    private TimePickerDialog mTimePicker;
    private String nikUser, userName;
    private int totalPelayananCounter = 100000;
    private SimpleDateFormat dateFormatter;
    private UserController userController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_berobat_activity);
        loadingDialog = new LoadingDialog(DaftarBerobatActivity.this);
        userController = new UserController(getApplicationContext());
        userController.setiDaftarPelayanan(this);
        userController.setDialog(loadingDialog);

        Bundle bundle = getIntent().getExtras();
        nikUser = bundle.getString("nikUser", "");
        userName = bundle.getString("userName", "");

        Toolbar tbDaftar = findViewById(R.id.toolbar);
        tbDaftar.setTitle("Daftar Berobat");
        setSupportActionBar(tbDaftar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        loadingDialog.show();
        dataPendaftaranEntityList = userController.getAllPasien();


        edtKodeBerobat = findViewById(R.id.edtKodeBerobat);
        edtNIK = findViewById(R.id.edtNIK);
        tvWaktuBerobat = findViewById(R.id.tvWaktuBerobat);
        tvValueTanggalBerobat = findViewById(R.id.tvValueTanggalBerobat);
        edtKeluhan = findViewById(R.id.edtKeluhan);
        spDokter = findViewById(R.id.spNamaDokter);
        spDaftarPelayanan = findViewById(R.id.spPilihPelayanan);
        btnDaftar = findViewById(R.id.btnDaftar);


        edtNIK.setText(nikUser);
        edtNIK.setEnabled(false);

        tvWaktuBerobat.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            mTimePicker = new TimePickerDialog(DaftarBerobatActivity.this, (timePicker, selectedHour, selectedMinute) ->
                    tvWaktuBerobat.setText(String.format("%02d:%02d", selectedHour, selectedMinute)), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        tvValueTanggalBerobat.setOnClickListener(v -> {
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvValueTanggalBerobat.setText(dateFormatter.format(newDate.getTime()));
            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });

        btnDaftar.setOnClickListener(v -> {
            if (spDokter.getSelectedItem().toString().isEmpty() || spDaftarPelayanan.getSelectedItem().toString().isEmpty()) {
                ToastUtility.showToast(this,"Harap Isi Field Dokter atau Jenis Pelayanan ");
            } else if (userController.cekField(edtKodeBerobat,edtNIK,edtKeluhan)) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                saveData();
            }  else {
                ToastUtility.showToast(this,"Harap Isi Field");
            }
        });
    }


    private void initSpinnerDokter(List<DokterEntity> dokterEntityList) {
        List<String> namaDokter = new ArrayList<>();
        for (DokterEntity entity: dokterEntityList) {
            namaDokter.add(entity.getNamaDokter());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item,namaDokter); //selected item will look like a spinner set from XML
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
    }

    private void initSpinnerDaftarPelayanan(List<DataPelayananEntity> dataPelayananEntityList) {
        List<String> namaPelayanan = new ArrayList<>();
        for (DataPelayananEntity entity: dataPelayananEntityList) {
            namaPelayanan.add(entity.getNamaPelayanan());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item,namaPelayanan); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaftarPelayanan.setAdapter(spinnerArrayAdapter);

        spDaftarPelayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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

    private void saveData(){
        String  kodeBerobat = edtKodeBerobat.getText().toString();
        String  nik = edtNIK.getText().toString();
        String  waktuBerobat =  tvWaktuBerobat.getText().toString();
        String  tanggalBerobat =  tvValueTanggalBerobat.getText().toString();
        String  keluhan =  edtKeluhan.getText().toString();
        String  namaDokter =  spDokter.getSelectedItem().toString();
        String  namaLayanan =  spDaftarPelayanan.getSelectedItem().toString();
        userController.setDaftarBerobat(kodeBerobat,nik,namaLayanan,namaDokter,tanggalBerobat,waktuBerobat,keluhan,"Waiting Approved");
    }

    @Override
    public void onSuccessDaftar() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        ToastUtility.showToast(this,"Selamat Pendaftaran Anda Berhasil");
    }

    @Override
    public void onFailedDaftar() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        ToastUtility.showToast(this,"Harap cek kembali data yang Anda masukkan");
    }

    @Override
    public void showDataDokter(List<DokterEntity> dokterEntityList) {
        initSpinnerDokter(dokterEntityList);
    }

    @Override
    public void showDataLayanan(List<DataPelayananEntity> dataPelayananEntityList) {
        initSpinnerDaftarPelayanan(dataPelayananEntityList);
        loadingDialog.dismiss();
    }

    @Override
    public void onShowDataPasien(List<DataPendaftaranEntity> pendaftaranEntities) {
        dataPendaftaranEntityList = pendaftaranEntities;
        checkKodeBerobat(dataPendaftaranEntityList);
        dokterEntityList = userController.getALLDataDokter();
        pelayananEntityList = userController.getAllLayanan();
    }

    private void checkKodeBerobat(List<DataPendaftaranEntity> pendaftaranEntities){
        if (pendaftaranEntities.size() == 0){
            edtKodeBerobat.setText("Berobat - " + 1);
        } else {
            int total = pendaftaranEntities.size() + 1;
            edtKodeBerobat.setText("Berobat - " + total);
        }
        edtKodeBerobat.setEnabled(false);
    }
}
