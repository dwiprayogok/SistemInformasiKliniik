package com.example.sisteminformasikliniik.View.Admin.Transaksi;

import android.app.DatePickerDialog;
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

import com.example.sisteminformasikliniik.Adapter.PembayaranAdapter;
import com.example.sisteminformasikliniik.Adapter.SearchDataLayananAdapter;
import com.example.sisteminformasikliniik.Adapter.SearchKodeRekamAdapter;
import com.example.sisteminformasikliniik.Adapter.SearchObatAdapter;
import com.example.sisteminformasikliniik.Controller.DataMasterController;
import com.example.sisteminformasikliniik.Controller.PembayaranController;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IPembayaran;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PembayaranActivity extends AppCompatActivity implements PembayaranAdapter.onActionPembayaranClicked,
        IPembayaran, SearchDataLayananAdapter.onActioLayananClicked, SearchObatAdapter.onActionObatClicked,
        SearchKodeRekamAdapter.onActionKodeRekamClicked{

    private LoadingDialog loadingDialog;
    private RecyclerView rvDataPembayaran;
    private PembayaranAdapter pembayaranAdapter;
    private ExtendedFloatingActionButton fab;
    private Dialog customDialog,searchObatDialog,searchRekamMedisDialog,searchLayananDialog;
    private Button btnSimpan,btnUpdate,btnHapus;
    private List<PembayaranEntity> pembayaranEntityList = new ArrayList<>();
    private List<DataObatEntity> obatEntityList = new ArrayList<>();
    private List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();
    private ArrayList<PembayaranEntity> dataForFilter;
    private List<DataPelayananEntity> pelayananEntities;
    private SearchDataLayananAdapter searchDataLayananAdapter;
    private SearchObatAdapter searchObatAdapter;
    private SimpleDateFormat dateFormatter;
    private SearchKodeRekamAdapter searchKodeRekamAdapter;
    private EditText
            edtKodeFaktur,
            edtKodeRekam ,
            edtNamaPasien  ,
            edtTanggalPeriksa ,
            edtAnamnesa  ,
            edtJenisLayanan,
            edtNamaPelayanan,
            edtBiayaPelayanan ,
            edtTotalBiayaLayanan,
            edtNamaObat,
            edtHargaObat,
            edtTotalBiayaObat,
            edtTotalBiayaBerobat,
            edtKeterangan,
            edtSearchPembayaran;
    private int idPayment;
    private PembayaranController controller;
    List<String> listNamaLayanan = new ArrayList<>();
    List<String> listHarga = new ArrayList<>();
    List<String> listNamaObat = new ArrayList<>();
    List<String> listHargaObat = new ArrayList<>();
    private String total = "";
    private int sumObat = 0;
    private int sumLayanan = 0;
    private int totalBerobat=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tambah_data_general);
        loadingDialog = new LoadingDialog(PembayaranActivity.this);
        controller = new PembayaranController(getApplicationContext(),loadingDialog);
        controller.setPembayaran(this);
        Toolbar tbPembayaran = findViewById(R.id.toolbar);
        tbPembayaran.setTitle("Pembayaran Pasien");
        fab = findViewById(R.id.fabAdd);
        setSupportActionBar(tbPembayaran);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        loadingDialog.show();
        pembayaranEntityList = controller.getAllDataPembayaran();
        dataForFilter = new ArrayList<>();
        rvDataPembayaran = findViewById(R.id.rvData);
        edtSearchPembayaran = findViewById(R.id.edtSearchData);
        edtSearchPembayaran.setHint("Kode Faktur");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setupSearchFeature();
        fab.setOnClickListener(v -> {
          DialogForm(null);
        });

    }

    private void setupSearchFeature(){
        edtSearchPembayaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                pembayaranAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void rvDataPembayaran(){
        final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvDataPembayaran.setLayoutManager(vertical);
        pembayaranAdapter = new PembayaranAdapter(pembayaranEntityList,dataForFilter,getApplicationContext(),this);
        rvDataPembayaran.setAdapter(pembayaranAdapter);
        pembayaranAdapter.updateDataList(pembayaranEntityList);

    }

    private void DialogForm(PembayaranEntity pembayaranEntity) {
        customDialog = new Dialog(PembayaranActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_tambah_pembayaran);
        customDialog.setCancelable(true);

        edtKodeFaktur    = customDialog.findViewById(R.id.edtKodeFaktur);
        edtKodeRekam   = customDialog.findViewById(R.id.edtKodeRekam);
        edtNamaPasien    = customDialog.findViewById(R.id.edtNamaPasien);
        edtTanggalPeriksa   = customDialog.findViewById(R.id.edtTanggalPeriksa);
        edtAnamnesa   = customDialog.findViewById(R.id.edtAnamnesa);
        edtJenisLayanan = customDialog.findViewById(R.id.edtJenisLayanan);
        edtNamaPelayanan = customDialog.findViewById(R.id.edtNamaLayanan);
        edtBiayaPelayanan    = customDialog.findViewById(R.id.edtBiayaPelayanan);
        edtTotalBiayaLayanan   = customDialog.findViewById(R.id.edtTotalBiayaLayanan);
        edtNamaObat    = customDialog.findViewById(R.id.edtNamaObat);
        edtHargaObat   = customDialog.findViewById(R.id.edtHargaObat);
        edtTotalBiayaObat    = customDialog.findViewById(R.id.edtTotalBiayaObat);
        edtTotalBiayaBerobat   = customDialog.findViewById(R.id.edtTotalBiayaBerobat);
        edtKeterangan    = customDialog.findViewById(R.id.edtKeterangan);

        btnSimpan = customDialog.findViewById(R.id.btnSimpanDialogTambahPembayaran);
        btnUpdate = customDialog.findViewById(R.id.btnUpdateDialogTambahPembayaran);
        btnHapus = customDialog.findViewById(R.id.btnHapusDialogTambahPembayaran);

        if (pembayaranEntity != null) {
            edtKodeFaktur.setEnabled(false);
            edtKodeRekam.setEnabled(false);
            edtKodeFaktur.setText(pembayaranEntity.getKodeFaktur());
            edtKodeRekam.setText(pembayaranEntity.getKodeRekam());
            edtNamaPasien.setText(pembayaranEntity.getNamaPasien());
            edtTanggalPeriksa.setText(pembayaranEntity.getTanggalPeriksa());
            edtAnamnesa.setText(pembayaranEntity.getAnamnesa());
            edtNamaPelayanan.setText(pembayaranEntity.getNamaLayanan());
            edtBiayaPelayanan.setText(pembayaranEntity.getBiayaLayanan());
            edtTotalBiayaLayanan.setText(pembayaranEntity.getTotalBiayaLayanan());
            edtNamaObat.setText(pembayaranEntity.getNamaObat());
            edtHargaObat.setText(pembayaranEntity.getHargaObat());
            edtTotalBiayaObat.setText(pembayaranEntity.getTotalBiayaObat());
            edtTotalBiayaBerobat.setText(pembayaranEntity.getTotalBiayaBerobat());
            edtKeterangan.setText(pembayaranEntity.getKeterangan());
            selectkoderekam(edtKodeRekam);
            edtTanggalPeriksa.setOnClickListener(v -> {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtTanggalPeriksa.setText(dateFormatter.format(newDate.getTime()));
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            });
            selectJenisLayanan(edtJenisLayanan,pelayananEntities);
            selectObat(edtNamaObat);
            btnSimpan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnHapus.setVisibility(View.VISIBLE);
        } else {
            checkKodeFaktur(edtKodeFaktur,pembayaranEntityList);
            edtKodeFaktur.setEnabled(false);
            selectkoderekam(edtKodeRekam);
            edtTanggalPeriksa.setOnClickListener(v -> {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtTanggalPeriksa.setText(dateFormatter.format(newDate.getTime()));
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            });
            selectJenisLayanan(edtJenisLayanan,pelayananEntities);
            selectObat(edtNamaObat);
        }

        listNamaLayanan.clear();
        listHarga.clear();
        listNamaObat.clear();
        listHargaObat.clear();

        btnSimpan.setOnClickListener(v -> {
            if (controller.cekField(
                    edtKodeFaktur ,
                    edtKodeRekam ,
                    edtNamaPasien ,
                    edtTanggalPeriksa ,
                    edtAnamnesa ,
                    edtNamaPelayanan,
                    edtBiayaPelayanan ,
                    edtTotalBiayaLayanan ,
                    edtNamaObat  ,
                    edtHargaObat ,
                    edtTotalBiayaObat  ,
                    edtTotalBiayaBerobat ,
                    edtKeterangan )) {

                controller.setTambahPembayaran(
                        edtKodeFaktur.getText().toString()   ,
                        edtKodeRekam.getText().toString()  ,
                        edtNamaPasien.getText().toString()  ,
                        edtTanggalPeriksa.getText().toString() ,
                        edtAnamnesa.getText().toString()   ,
                        edtNamaPelayanan.getText().toString(),
                        edtBiayaPelayanan.getText().toString()  ,
                        edtTotalBiayaLayanan.getText().toString() ,
                        edtNamaObat.getText().toString()   ,
                        edtHargaObat.getText().toString()   ,
                        edtTotalBiayaObat.getText().toString()   ,
                        edtTotalBiayaBerobat.getText().toString()  ,
                        edtKeterangan.getText().toString(),
                        "Paid");
            } else {
                ToastUtility.showToast(this,"Masih ada data yang kosong!");
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (controller.cekField(edtKodeFaktur ,
                    edtKodeRekam ,
                    edtNamaPasien ,
                    edtTanggalPeriksa ,
                    edtAnamnesa ,
                    edtNamaPelayanan,
                    edtBiayaPelayanan ,
                    edtTotalBiayaLayanan ,
                    edtNamaObat  ,
                    edtHargaObat ,
                    edtTotalBiayaObat  ,
                    edtTotalBiayaBerobat ,
                    edtKeterangan)) {
                controller.setUpdatePembayaran(
                        edtKodeFaktur.getText().toString()   ,
                        edtKodeRekam.getText().toString()  ,
                        edtNamaPasien.getText().toString()  ,
                        edtTanggalPeriksa.getText().toString() ,
                        edtAnamnesa.getText().toString()   ,
                        edtNamaPelayanan.getText().toString(),
                        edtBiayaPelayanan.getText().toString()  ,
                        edtTotalBiayaLayanan.getText().toString() ,
                        edtNamaObat.getText().toString()   ,
                        edtHargaObat.getText().toString()   ,
                        edtTotalBiayaObat.getText().toString()   ,
                        edtTotalBiayaBerobat.getText().toString()  ,
                        edtKeterangan.getText().toString(),
                        "Paid" );
            }else {
                ToastUtility.showToast(this,"Masih ada data yang kosong!");
            }
        });

        btnHapus.setOnClickListener(v -> new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Apakah Anda yakin ingin menghapus data ini ?")
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    controller.deleteDataPembayaran(edtKodeFaktur.getText().toString());
                })
                .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                .show());


        customDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
        refreshData(pembayaranEntityList);
    }

    @Override
    public void onEdit(PembayaranEntity pembayaranEntity) {
        DialogForm(pembayaranEntity);
    }


    @Override
    public void onSuccessAddPembayaran(List<PembayaranEntity> pembayaranEntities) {
        ToastUtility.showToast(this,"Data berhasil di simpan");
        customDialog.dismiss();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        pembayaranEntityList = pembayaranEntities;
        refreshData(pembayaranEntityList);
    }

    @Override
    public void onSuccessUpdatePembayaran(List<PembayaranEntity> pembayaranEntities) {
        ToastUtility.showToast(this,"Data berhasil di Update");
        customDialog.dismiss();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        pembayaranEntityList = pembayaranEntities;
        refreshData(pembayaranEntityList);
    }

    @Override
    public void onSuccessDeletePembayaran(List<PembayaranEntity> pembayaranEntities) {
        ToastUtility.showToast(this,"Data berhasil di hapus");
        customDialog.dismiss();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        pembayaranEntityList = pembayaranEntities;
        refreshData(pembayaranEntityList);
    }

    @Override
    public void onFailed(String error) {
        ToastUtility.showToast(this,error);
        loadingDialog.dismiss();
    }

    private void refreshData(List<PembayaranEntity> pembayaranEntityList){
        pembayaranAdapter = new PembayaranAdapter(pembayaranEntityList,dataForFilter,getApplicationContext(),this);
        rvDataPembayaran.setAdapter(pembayaranAdapter);
        pembayaranAdapter.updateDataList(pembayaranEntityList);
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
    public void showData(List<DataPelayananEntity> dataPelayananEntityList) {
        pelayananEntities = dataPelayananEntityList;
        obatEntityList = controller.getALLObat();
    }

    @Override
    public void showDataObat(List<DataObatEntity> dataObatEntityList) {
        obatEntityList = dataObatEntityList;
        rekamMedisEntityList = controller.getALLDataRekamMedisPasien("Done");
    }

    private void selectJenisLayanan(EditText edtJenisLayanan, List<DataPelayananEntity> pelayananEntityLisy) {
        edtJenisLayanan.setOnClickListener(v -> {
            searchLayananDialog=new Dialog(PembayaranActivity.this);
            searchLayananDialog.setContentView(R.layout.dialog_searchable_spinner);
            searchLayananDialog.show();
            TextView tvTitle = searchLayananDialog.findViewById(R.id.titleDialog);
            EditText editText = searchLayananDialog.findViewById(R.id.search_data);
            RecyclerView rvObat = searchLayananDialog.findViewById(R.id.rvSearchData);

            tvTitle.setText("Pilih Layanan");

            final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
            rvObat.setLayoutManager(vertical);
            searchDataLayananAdapter = new SearchDataLayananAdapter(pelayananEntityLisy,PembayaranActivity.this::onItemClicked);
            rvObat.setAdapter(searchDataLayananAdapter);
            searchDataLayananAdapter.updateDataList(pelayananEntityLisy);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchDataLayananAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        });
    }

    private void selectObat(EditText edtNamaObat) {
        edtNamaObat.setOnClickListener(v -> {
            searchObatDialog=new Dialog(PembayaranActivity.this);
            searchObatDialog.setContentView(R.layout.dialog_searchable_spinner);
            searchObatDialog.show();
            TextView tvTitle = searchObatDialog.findViewById(R.id.titleDialog);
            EditText editText=searchObatDialog.findViewById(R.id.search_data);
            RecyclerView rvObat=searchObatDialog.findViewById(R.id.rvSearchData);

            tvTitle.setText("Pilih Obat");

            final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
            rvObat.setLayoutManager(vertical);
            searchObatAdapter = new SearchObatAdapter(obatEntityList,PembayaranActivity.this::onItemClicked);
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

    private void selectkoderekam(EditText edtKodeRekam) {
        edtKodeRekam.setOnClickListener(v -> {
            searchRekamMedisDialog=new Dialog(PembayaranActivity.this);
            searchRekamMedisDialog.setContentView(R.layout.dialog_searchable_spinner);
            searchRekamMedisDialog.show();
            TextView tvTitle = searchRekamMedisDialog.findViewById(R.id.titleDialog);
            EditText editText=searchRekamMedisDialog.findViewById(R.id.search_data);
            RecyclerView rvRekam=searchRekamMedisDialog.findViewById(R.id.rvSearchData);

            tvTitle.setText("Pilih Kode Rekam");

            final LinearLayoutManager vertical = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
            rvRekam.setLayoutManager(vertical);
            searchKodeRekamAdapter = new SearchKodeRekamAdapter(rekamMedisEntityList,PembayaranActivity.this::onItemClicked);
            rvRekam.setAdapter(searchKodeRekamAdapter);
            searchKodeRekamAdapter.updateDataList(rekamMedisEntityList);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchKodeRekamAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        });
    }

    @Override
    public void onItemClicked(DataPelayananEntity dataPelayananEntity, View view) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbHarga = new StringBuilder();
        if (listNamaLayanan.size() == 0) {
            listNamaLayanan.add(dataPelayananEntity.getNamaPelayanan());
        } else{
            listNamaLayanan.add(dataPelayananEntity.getNamaPelayanan());
        }

        if (listHarga.size() == 0) {
            listHarga.add(dataPelayananEntity.getBiayaPelayanan());
        }else {
            listHarga.add(dataPelayananEntity.getBiayaPelayanan());
        }

        for (int i =0; i<listNamaLayanan.size(); i++){
            sb.append(listNamaLayanan.get(i)).append("\n");
        }

        for (int i =0; i<listHarga.size(); i++){
            sbHarga.append(listHarga.get(i)).append("\n");
        }

        edtNamaPelayanan.setText(sb.toString());
        edtBiayaPelayanan.setText(sbHarga);
        List<Integer> totalBiayLayanan = new ArrayList<>();
        for (int i =0; i < listHarga.size();i++) {
            total = listHarga.get(i).replace("Rp","").replace(".","");
            totalBiayLayanan.add(Integer.parseInt(total));
        }

        for (int i : totalBiayLayanan)
            sumLayanan = sumLayanan + i;

        edtTotalBiayaLayanan.setText("Rp."+sumLayanan);

        totalBerobat = sumLayanan + sumObat;
        edtTotalBiayaBerobat.setText("Rp."+totalBerobat);
    }

    @Override
    public void checkDataPembayaran(List<PembayaranEntity> pembayaranEntities) {
        pembayaranEntityList = pembayaranEntities;
        pelayananEntities = controller.getAllLayanan();
    }

    private void checkKodeFaktur(EditText edtKodeFaktur, List<PembayaranEntity> pembayaranEntityList){
        if (pembayaranEntityList.size() == 0){
            edtKodeFaktur.setText("Pembayaran - " + 1);
        } else {
            int total = pembayaranEntityList.size() + 1;
            edtKodeFaktur.setText("Pembayaran - " + total);
        }
        edtKodeFaktur.setEnabled(false);

    }

    @Override
    public void onItemClicked(DataObatEntity dataObatEntity, View view) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbHarga = new StringBuilder();
        if (listNamaObat.size() == 0) {
            listNamaObat.add(dataObatEntity.getNamaObat());
        } else {
            listNamaObat.add(dataObatEntity.getNamaObat());
        }

        if (listHargaObat.size() == 0) {
            listHargaObat.add(dataObatEntity.getHargaObat());
        } else {
            listHargaObat.add(dataObatEntity.getHargaObat());
        }

        for (int i =0; i<listNamaObat.size(); i++){
            sb.append(listNamaObat.get(i)).append("\n");
        }

        for (int i =0; i<listHargaObat.size(); i++){
            sbHarga.append(listHargaObat.get(i)).append("\n");
        }

        edtNamaObat.setText(sb);
        edtHargaObat.setText(sbHarga);
        edtTotalBiayaObat.setText(edtHargaObat.getText().toString());
    }

    @Override
    public void showAllDataRekamMedis(List<RekamMedisEntity> listRekamMedis) {
        rekamMedisEntityList = listRekamMedis;
        loadingDialog.dismiss();
        rvDataPembayaran();
    }

    @Override
    public void onItemClicked(RekamMedisEntity rekamMedisEntity, View view) {
        StringBuilder sbHargaObat =new StringBuilder();
        listNamaLayanan.clear();
        listNamaObat.clear();
        edtKodeRekam.setText(rekamMedisEntity.getKodeRekam());
        edtNamaPasien.setText(rekamMedisEntity.getNamaPasien());
        edtTanggalPeriksa.setText(rekamMedisEntity.getTanggalPeriksa());
        edtAnamnesa.setText(rekamMedisEntity.getAnamnesa());

        if (listNamaLayanan.size()==0) {
            listNamaLayanan.add(rekamMedisEntity.getPelayanan());
        } else {
            listNamaLayanan.add(rekamMedisEntity.getPelayanan());
        }
        for (String s: listNamaLayanan) {
            edtNamaPelayanan.setText(s);
        }

         for (int i=0; i < pelayananEntities.size(); i++) {
                 for (int j = 0; j < listNamaLayanan.size(); j++) {
                     if (pelayananEntities.get(i).getNamaPelayanan().equals(listNamaLayanan.get(j))) {
                         listHarga.add(pelayananEntities.get(i).getBiayaPelayanan());
                         edtBiayaPelayanan.setText(pelayananEntities.get(i).getBiayaPelayanan());
                         break;
                     }

                 }
         }
        int biayaLayanan = 0;
        edtTotalBiayaLayanan.setText(edtBiayaPelayanan.getText().toString());
         if (edtBiayaPelayanan.getText().toString().isEmpty()) {
             biayaLayanan = 0;
         } else {
             biayaLayanan = Integer.parseInt(edtBiayaPelayanan.getText().toString().replace("Rp","").replace(".",""));
         }

        if (listNamaObat.size()==0) {
            listNamaObat.add(rekamMedisEntity.getTherapi());
        } else {
            listNamaObat.add(rekamMedisEntity.getTherapi());
        }

        for (String s: listNamaObat) {
            edtNamaObat.setText(s);
        }
        for (int i=0; i < obatEntityList.size(); i++) {
            String [] namaObat = listNamaObat.get(0).split("\n");
            for (String namaObat2 : namaObat) {
                if (obatEntityList.get(i).getNamaObat().equals(namaObat2)) {
                    listHargaObat.add(obatEntityList.get(i).getHargaObat());
                }
            }
        }
        int totalHargaObat = 0;
        for (int i =0; i<listHargaObat.size(); i++){
            sbHargaObat.append(listHargaObat.get(i)).append("\n");
        }
        edtHargaObat.setText(sbHargaObat);

        List<Integer> totalBiayaObat = new ArrayList<>();

        for (int i =0; i < listHargaObat.size();i++) {
            total = listHargaObat.get(i).replace("Rp","").replace(".","");
            totalBiayaObat.add(Integer.parseInt(total));
        }

        for (int i : totalBiayaObat) {
            sumObat = sumObat + i;
        }
        edtTotalBiayaObat.setText("Rp."+sumObat);
        totalBerobat = biayaLayanan + sumObat;
        edtTotalBiayaBerobat.setText("Rp."+totalBerobat);

        edtKeterangan .setText(rekamMedisEntity.getKeterangan());
        searchRekamMedisDialog.dismiss();
    }


}
