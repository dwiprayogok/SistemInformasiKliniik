package com.example.sisteminformasikliniik.View.User;

import android.app.Dialog;
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

import com.example.sisteminformasikliniik.Adapter.KartuBerobatAdapter;
import com.example.sisteminformasikliniik.Controller.UserController;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.Model.KartuBerobat;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.IKartuBerobat;

import java.util.ArrayList;
import java.util.List;

public class KartuBerobatActivity extends AppCompatActivity implements
        KartuBerobatAdapter.onActionKartuClicked, IKartuBerobat {

    private RecyclerView rvDataKartuBerobat;
    private EditText edtSearchPasien;
    private LoadingDialog loadingDialog;
    private Dialog customDialog;
    private KartuBerobatAdapter kartuBerobatAdapter;
    private List<KartuBerobat> kartuBerobatList = new ArrayList<>();
    private ArrayList<KartuBerobat> dataForFilter;
    private String nikPasien, userName;
    private UserController userController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_show_data_general);
        loadingDialog = new LoadingDialog(KartuBerobatActivity.this);
        userController = new UserController(getApplicationContext());
        userController.setDialog(loadingDialog);
        userController.setiKartuBerobat(this);
        Bundle bundle = getIntent().getExtras();
        nikPasien    = bundle.getString("nikUser", "");
        userName = bundle.getString("userName", "");
        Toolbar tbPasien = findViewById(R.id.toolbar);
        tbPasien.setTitle("Kartu Berobat");
        setSupportActionBar(tbPasien);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dataForFilter = new ArrayList<>();
        loadingDialog.show();
        kartuBerobatList = userController.getKartuBerobat("Done",nikPasien);
        rvDataKartuBerobat = findViewById(R.id.rvData);
        edtSearchPasien = findViewById(R.id.edtSearchData);
        edtSearchPasien.setHint("Kode Berobat");
        setRvDataKartuBerobat(kartuBerobatList);
        setupSearchFeature();
    }

    private void setRvDataKartuBerobat(List<KartuBerobat> berobatList) {
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataKartuBerobat.setLayoutManager(vertical);
        kartuBerobatAdapter = new KartuBerobatAdapter(berobatList,dataForFilter,this);
        rvDataKartuBerobat.setAdapter(kartuBerobatAdapter);
        kartuBerobatAdapter.updateDataList(berobatList);
    }

    private void setupSearchFeature(){
        edtSearchPasien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                kartuBerobatAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        refreshData(kartuBerobatList);
    }

    private void refreshData(List<KartuBerobat> kartuBerobatList){

        kartuBerobatAdapter = new KartuBerobatAdapter(kartuBerobatList,dataForFilter,this);
        rvDataKartuBerobat.setAdapter(kartuBerobatAdapter);
        kartuBerobatAdapter.updateDataList(kartuBerobatList);
        loadingDialog.dismiss();
    }

    @Override
    public void onItemClicked(KartuBerobat berobat, View view) {
        DialogForm(berobat);
    }

    private void DialogForm(KartuBerobat kartuBerobat) {
        customDialog = new Dialog(KartuBerobatActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.detail_kartu_berobat);
        customDialog.setCancelable(true);

        EditText edtKodeBerobat    = customDialog.findViewById(R.id.edtKodeBerobat);
        EditText edtKodeRekam   = customDialog.findViewById(R.id.edtKodeRekam);
        EditText edtNama    = customDialog.findViewById(R.id.edtNama);
        EditText edtStatus    = customDialog.findViewById(R.id.edtStatus);
        EditText edtUsia   = customDialog.findViewById(R.id.edtUsia);
        TextView tvValueTanggalPeriksa   = customDialog.findViewById(R.id.tvValueTanggalPeriksa);
        EditText edtNamaDokterDialogDetailKartuBerobat  = customDialog.findViewById(R.id.edtNamaDokterDialogDetailKartuBerobat);
        EditText edtAnamnesa   = customDialog.findViewById(R.id.edtAnamnesa);
        EditText edtTherapi   = customDialog.findViewById(R.id.edtTherapi);
        EditText edtKeterangan = customDialog.findViewById(R.id.edtKeterangan);

        if (kartuBerobat != null) {
             edtKodeBerobat.setEnabled(false);
             edtKodeRekam.setEnabled(false);
             edtStatus.setEnabled(false);

             edtKodeBerobat.setText(kartuBerobat.getKodeBerobat());
             edtKodeRekam.setText(kartuBerobat.getKodeRekam());
             edtStatus.setText(kartuBerobat.getStatus());

             edtNama.setText(kartuBerobat.getNamaLengkapPasien());
             edtUsia.setText(kartuBerobat.getUmurPasien());
             tvValueTanggalPeriksa.setText(kartuBerobat.getTanggalBerobat());

             edtNamaDokterDialogDetailKartuBerobat.setText(kartuBerobat.getNamaDokter());
             edtAnamnesa.setText(kartuBerobat.getAnamnesa());
             edtTherapi.setText(kartuBerobat.getTherapi());
             edtKeterangan.setText(kartuBerobat.getKeterangan());
        }
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

    @Override
    public void onFailedShowData(String error) {
        ToastUtility.showToast(this,error);
    }

    @Override
    public void showDetail(List<KartuBerobat> berobatList) {
            loadingDialog.dismiss();
            kartuBerobatList = berobatList;
            refreshData(kartuBerobatList);
    }
}
