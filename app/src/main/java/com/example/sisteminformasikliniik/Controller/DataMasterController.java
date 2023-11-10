package com.example.sisteminformasikliniik.Controller;

import android.widget.EditText;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IPembayaran;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterDokter;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterLayanan;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterPasien;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterPemilik;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IDataMasterObat;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMasterController {

    private KlinikDatabase klinikDatabase;
    IPembayaran iPembayaran;
    IDataMasterDokter iDataMasterDokter;
    IDataMasterObat iDataMasterObat;
    IDataMasterLayanan iDataMasterLayanan;
    IDataMasterPemilik iDataMasterPemilik;
    IDataMasterPasien dataMasterPasien;
    private LoadingDialog dialog;

    private List<DokterEntity> dokterEntityList = new ArrayList<>();
    private List<DataObatEntity> dataObatEntityList = new ArrayList<>();
    private List<DataPelayananEntity> dataPelayananEntityList = new ArrayList<>();
    private List<DataPemilikEntity> dataPemilikEntityList = new ArrayList<>();
    private List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    private List<PembayaranEntity> pembayaranEntityList = new ArrayList<>();
    private List<UserEntity> userEntityList = new ArrayList<>();


    public DataMasterController() {
    }

    public DataMasterController(KlinikDatabase klinikDatabase) {
        this.klinikDatabase = klinikDatabase;
    }

    public void setiDataMasterLayanan(IDataMasterLayanan iDataMasterLayanan) {
        this.iDataMasterLayanan = iDataMasterLayanan;
    }

    public void setDataMasterPasien(IDataMasterPasien dataMasterPasien) {
        this.dataMasterPasien = dataMasterPasien;
    }

    public void setiDataMasterPemilik(IDataMasterPemilik iDataMasterPemilik) {
        this.iDataMasterPemilik = iDataMasterPemilik;
    }

    public void setiDataaMasterObat(IDataMasterObat iDataMasterObat) {
        this.iDataMasterObat = iDataMasterObat;
    }

    public void setDialog(LoadingDialog dialog) {
        this.dialog = dialog;
    }


    public void setiPembayaran(IPembayaran iPembayaran) {
        this.iPembayaran = iPembayaran;
    }

    public void setiDataMasterDokter(IDataMasterDokter iDataMasterDokter) {
        this.iDataMasterDokter = iDataMasterDokter;
    }

    public boolean cekField(EditText...edt) {
        for (EditText editText : edt) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Harap Di isi");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public void clearField(EditText...edt) {
        for (EditText editText : edt) {
            if (!editText.getText().toString().isEmpty()) {
                editText.setText("");
            }
        }
    }




    public void saveDataObat(String kodeObat, String namaObat, String hargaObat){
        DataObatEntity obatEntity = new DataObatEntity();
        obatEntity.setKodeObat(kodeObat);
        obatEntity.setNamaObat(namaObat);
        obatEntity.setHargaObat(hargaObat);
        klinikDatabase.obatDao().tambahDataObat(obatEntity);

    }

    public void updateDataObat(int idObat, String kodeObat, String namaObat, String hargaObat){
        DataObatEntity obatEntity = new DataObatEntity();
        obatEntity.setId(idObat);
        obatEntity.setKodeObat(kodeObat);
        obatEntity.setNamaObat(namaObat);
        obatEntity.setHargaObat(hargaObat);
        klinikDatabase.obatDao().update(obatEntity);

    }



    public void saveDataPelayanan(String kodePelayanan, String namaPelayanan, String biayaLayanan){
        DataPelayananEntity pelayananEntity = new DataPelayananEntity();
        pelayananEntity.setKodePelayanan(kodePelayanan);
        pelayananEntity.setNamaPelayanan(namaPelayanan);
        pelayananEntity.setBiayaPelayanan(biayaLayanan);
        klinikDatabase.pelayananDao().tambahDataPelayanan(pelayananEntity);
    }

    public void updateDataPelayanan(int idPelayanan, String kodePelayanan, String namaPelayanan, String biayaLayanan){
        DataPelayananEntity pelayananEntity = new DataPelayananEntity();
        pelayananEntity.setId(idPelayanan);
        pelayananEntity.setKodePelayanan(kodePelayanan);
        pelayananEntity.setNamaPelayanan(namaPelayanan);
        pelayananEntity.setBiayaPelayanan(biayaLayanan);
        klinikDatabase.pelayananDao().update(pelayananEntity);

    }

    public void deleteDataPelayanan(DataPelayananEntity dataPelayananEntity) {
        klinikDatabase.pelayananDao().delete(dataPelayananEntity);

    }

    public void setTambahDokter (String username, String password, String nik, String namaDokter, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().
                TambahDokter(username,password,nik,namaDokter,noHP,alamat,"Dokter");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        //saveDataDokter(username,password,nik,namaDokter,noHP,alamat);
                        iDataMasterDokter.onSuccessSaveData(dokterEntityList);
                    } else {
                        iDataMasterDokter.onFailed();
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterDokter.onFailed();
            }
        });
    }

    public void setUpdateDokter (String username, String password, String nik, String namaDokter, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().updateDokter(username,password,nik,namaDokter,noHP,alamat,"Dokter");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        iDataMasterDokter.onSuccessUpdateData(dokterEntityList);
                    } else {
                        iDataMasterDokter.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterDokter.onFailed();
            }
        });
    }



    public List<DokterEntity> getALLDataDokter() {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataDokter();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        iDataMasterDokter.showData(dokterEntityList);
                    } else {
                        iDataMasterDokter.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterDokter.onFailed();
            }
        });
        return dokterEntityList;
    }

    public void deleteDataDokter(String nik) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataDokter(nik);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        iDataMasterDokter.onSuccessDeleteData(dokterEntityList);
                    } else {
                        iDataMasterDokter.onFailed();
                    }
                }catch (NullPointerException e) {
                        e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                iDataMasterDokter.onFailed();
            }
        });
    }



    public void setTambahLayanan(String kodePelayanan, String namaPelayanan, String biayaLayanan) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().TambahLayanan(kodePelayanan,namaPelayanan,biayaLayanan);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPelayananEntityList = response.body().getResultLayanan();
                    if (value.equals("1")) {
                        iDataMasterLayanan.onSuccessSaveData(dataPelayananEntityList);
                    } else {
                        iDataMasterLayanan.onFailed();
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterLayanan.onFailed();
            }
        });
    }

    public void setupdateLayanan(String kodePelayanan, String namaPelayanan, String biayaLayanan) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().UpdateLayanan(kodePelayanan,namaPelayanan,biayaLayanan);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPelayananEntityList = response.body().getResultLayanan();
                    if (value.equals("1")) {
                        iDataMasterLayanan.onSuccessUpdateData(dataPelayananEntityList);
                    } else {
                        iDataMasterLayanan.onFailed();
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterLayanan.onFailed();
            }
        });
    }

    public List<DataPelayananEntity> getAllLayanan() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataLayanan();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPelayananEntityList = response.body().getResultLayanan();
                    if (value.equals("1")) {
                        iDataMasterLayanan.showData(dataPelayananEntityList);
                        iPembayaran.showData(dataPelayananEntityList);
                    } else {
                        iDataMasterLayanan.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                iDataMasterDokter.onFailed();
            }
        });
        return dataPelayananEntityList;
    }

    public void deleteDataLayanan(String kodePelayanan) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataLayanan(kodePelayanan);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPelayananEntityList = response.body().getResultLayanan();
                    if (value.equals("1")) {
                        iDataMasterLayanan.onSuccessDeleteData(dataPelayananEntityList);
                    } else {
                        iDataMasterLayanan.onFailed();
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                iDataMasterLayanan.onFailed();
            }
        });
    }



    public void setTambahObat (String kodeObat, String namaObat, String hargaObat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().TambahObat(kodeObat,namaObat,hargaObat);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataObatEntityList = response.body().getResultObat();
                    if (value.equals("1")) {
                        iDataMasterObat.onSuccessSaveDataObat(dataObatEntityList);
                    } else {
                        iDataMasterObat.onFailed();
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterObat.onFailed();
            }
        });
    }

    public void setUpdateObat (String kodeObat, String namaObat, String hargaObat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().UpdateObat(kodeObat,namaObat,hargaObat);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataObatEntityList = response.body().getResultObat();
                    if (value.equals("1")) {
                        iDataMasterObat.onSuccessUpdateDataObat(dataObatEntityList);
                    } else {
                        iDataMasterObat.onFailed();
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterObat.onFailed();
            }
        });
    }

    public List<DataObatEntity> getALLObat() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataObat();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataObatEntityList = response.body().getResultObat();
                    if (value.equals("1")) {
                        iDataMasterObat.showData(dataObatEntityList);
                    } else {
                        iDataMasterObat.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                iDataMasterObat.onFailed();
            }
        });
        return dataObatEntityList;
    }

    public void deleteDataObat(String kodeObat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataObat(kodeObat);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataObatEntityList = response.body().getResultObat();
                    if (value.equals("1")) {
                        iDataMasterObat.onSuccessDeleteData(dataObatEntityList);
                    } else {
                        iDataMasterObat.onFailed();
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                iDataMasterObat.onFailed();
            }
        });
    }


    public void setTambahPemilik (String username, String password, String nik, String namaDokter, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().TambahPemilik(
                username,password,nik,namaDokter,noHP,alamat, "Owner");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        iDataMasterPemilik.onSuccessSaveData(dataPemilikEntityList);
                    } else {
                        iDataMasterPemilik.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                    iDataMasterPemilik.onFailed(e.getMessage());
                }
                finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterPemilik.onFailed(t.getMessage());
            }
        });
    }

    public void setUpdatePemilik (String username, String password, String nik, String namaDokter, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().UpdatePemilik(username,password,nik,namaDokter,noHP,alamat,"Owner");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        iDataMasterPemilik.onSuccessUpdateData(dataPemilikEntityList);
                    } else {
                        iDataMasterPemilik.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iDataMasterPemilik.onFailed(t.getMessage());
            }
        });
    }

    public List<DataPemilikEntity> getALLPemilik() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataPemilik();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        iDataMasterPemilik.showData(dataPemilikEntityList);
                    } else {
                        iDataMasterPemilik.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                iDataMasterPemilik.onFailed(t.getMessage());
            }
        });
        return dataPemilikEntityList;
    }

    public void deleteDataPemilik(String nik) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataPemilik(nik);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        iDataMasterPemilik.onSuccessDeleteData(dataPemilikEntityList);
                    } else {
                        iDataMasterPemilik.onFailed(response.body().getMessage());
                    }
                }catch (NullPointerException e) {
                    iDataMasterPemilik.onFailed(e.getMessage());
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                iDataMasterPemilik.onFailed(t.getMessage());
            }
        });
    }

    public List<UserEntity> getAllDataPasien() {
        Call<ResponseError> call = ApiClient.getInstance().getReportServices().AllReportDataPasien();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        dataMasterPasien.onDetailDataPasien(userEntityList);
                    } else {
                        dataMasterPasien.onDetailDataPasien(new ArrayList<>());
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                    dataMasterPasien.onDetailDataPasien(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                dataMasterPasien.onDetailDataPasien(new ArrayList<>());
            }
        });
        return userEntityList;
    }


    // untuk DATA MASTER PASIEN
    public void getDataUserByNIk(String nik) {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().getDataUserByNik(nik);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        dataMasterPasien.onDetailDataPasien(userEntityList);
                    } else {
                        dataMasterPasien.onDetailDataPasien(new ArrayList<>());
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                    dataMasterPasien.onDetailDataPasien(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                dialog.dismiss();
                dataMasterPasien.onDetailDataPasien(new ArrayList<>());
            }
        });
    }

    public void deleteDataPasien(String nik) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataPasien(nik);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        dataMasterPasien.onSuccessDeleteDataPasien(userEntityList);
                    } else {
                        dataMasterPasien.onSuccessDeleteDataPasien(new ArrayList<>());
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                dataMasterPasien.onSuccessDeleteDataPasien(new ArrayList<>());
            }
        });
    }


    public void setUpdateProfile(String username, String password, String nik,
            String nama, String gender, String usia,
            String alamat, String pekerjaan, String noHP) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getiRegistrasiService().updateProfile(username, password, nik, nama, gender,
                usia, alamat, pekerjaan, noHP,"Pasien");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        dataMasterPasien.onSuccessUpdateDataPasien(userEntityList);
                    } else {

                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();

            }
        });
    }



}
