package com.example.sisteminformasikliniik.Controller;

import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IKonfirmasiPasien;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiPasienController {

    private KlinikDatabase klinikDatabase;
    private List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    private IKonfirmasiPasien iKonfirmasiPasien;
    private List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();
    private LoadingDialog dialog;

    public void setiKonfirmasiPasien(IKonfirmasiPasien iKonfirmasiPasien) {
        this.iKonfirmasiPasien = iKonfirmasiPasien;
    }


    public void setDialog(LoadingDialog dialog) {
        this.dialog = dialog;
    }

    public void setKlinikDatabase(KlinikDatabase klinikDatabase) {
        this.klinikDatabase = klinikDatabase;
    }

    public KonfirmasiPasienController() {
    }

    public List<DataPendaftaranEntity> dataPendaftaranEntityList(){
        List<DataPendaftaranEntity> list =  new ArrayList<>();
        list =  klinikDatabase.pendaftaranDao().selectByStatus("Pending");
        return  list;
    }

    public void clearList (List<DataPendaftaranEntity> list){
        list.clear();
    }

    public void saveToRekamMedis(
            String kodeBerobat,String nik,String jenisPelayanan, String namaDokter,
            String tanggalBerobat, String waktuBerobat,String keluhan ) {


        RekamMedisEntity rekamMedisEntity = new RekamMedisEntity();
        rekamMedisEntity.setKodeBerobat(kodeBerobat);
        rekamMedisEntity.setKodeRekam("");
        rekamMedisEntity.setNamaPasien("");
        rekamMedisEntity.setUmur("");
        rekamMedisEntity.setTanggalPeriksa(tanggalBerobat);
        rekamMedisEntity.setNamaDokter(namaDokter);
        rekamMedisEntity.setAnamnesa("");
        rekamMedisEntity.setTherapi("");
        rekamMedisEntity.setKeterangan(keluhan);
        klinikDatabase.rekamMedisDao().registerRekamMedis(rekamMedisEntity);
    }

    public void updateDaftarPelayanan(int idPendaftaran, String kodeBerobat, String nik, String namaPasien,
                                    String waktu, String keluhan, String namaDokter, String daftarPelayanan, String status){
        DataPendaftaranEntity dataPendaftaranEntity = new DataPendaftaranEntity();
        dataPendaftaranEntity.setId(idPendaftaran);
        dataPendaftaranEntity.setKodeBerobat(kodeBerobat);
        dataPendaftaranEntity.setNik(nik);
        dataPendaftaranEntity.setNamaPasien(namaPasien);
        dataPendaftaranEntity.setWaktuPelayanan(waktu);
        dataPendaftaranEntity.setKeluhan(keluhan);
        dataPendaftaranEntity.setNamaDokter(namaDokter);
        dataPendaftaranEntity.setJenisPelayanan(daftarPelayanan);
        dataPendaftaranEntity.setStatus(status);
        klinikDatabase.pendaftaranDao().update(dataPendaftaranEntity);
    }


    public void setTambahRekamMedis ( String kodeBerobat,String kodeRekam,String nama, String umur,
                                      String tanggalBerobat ,String namaDokter, String anamnesa,
                                      String therapi,String keterangan) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().
                TambahRekamMedis(kodeBerobat,kodeRekam,nama,umur,tanggalBerobat,namaDokter,anamnesa,therapi,keterangan,"Waiting Approved By Dokter");

        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onSuccessSaveDataRekamMedisPasien(rekamMedisEntityList);
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

    public List<DataPendaftaranEntity> getAllDataPasienBerobatNeedConfirmation() {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().AllDataPasienDaftarBerobatNeedConfirmation();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String usia = response.body().getUsia();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onShowData(dataPendaftaranEntityList,usia);
                    } else {

                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
            t.getMessage();

            }
        });
        return dataPendaftaranEntityList;
    }


    public List<DataPendaftaranEntity> getAllDataPasienBerobat() {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().AllDataPasienDaftarBerobat();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String usia = response.body().getUsia();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onShowData(dataPendaftaranEntityList,usia);
                    } else {

                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();

            }
        });
        return dataPendaftaranEntityList;
    }

    public List<DataPendaftaranEntity> getAllDataPasienApproved(String status) {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().getDataPasienApprove(status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String usia = response.body().getUsia();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onShowData(dataPendaftaranEntityList,usia);
                    } else {

                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();

            }
        });
        return dataPendaftaranEntityList;
    }

    public void setUpdatePasienApproved(String kodeBerobat, String nik, String namaPasien,
                                 String waktu, String keluhan, String namaDokter, String daftarPelayanan, String tanggalBerobat, String status) {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().updateDataPasienBerobat(
                kodeBerobat,nik,daftarPelayanan,namaDokter,tanggalBerobat,waktu,keluhan,status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onSuccessUpdateDataPasien(dataPendaftaranEntityList,"");
                    } else {

                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {


            }
        });
    }

    public void setUpdatePasientoRefused(String kodeBerobat, String nik, String namaPasien,
                                          String waktu, String keluhan, String namaDokter, String daftarPelayanan, String tanggalBerobat, String status) {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().updateDataPasienBerobat(
                kodeBerobat,nik,daftarPelayanan,namaDokter,tanggalBerobat,waktu,keluhan,status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onRefusedDataRekamMedisPasien(dataPendaftaranEntityList);
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {


            }
        });
    }


    public void deleteDataPasien(String nik) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().deleteDataPasienBerobat(nik);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPendaftaranEntityList= response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onSuccessDeleteDataPasienBerobat(dataPendaftaranEntityList);
                    } else {
                        iKonfirmasiPasien.onSuccessDeleteDataPasienBerobat(new ArrayList<>());
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
                iKonfirmasiPasien.onSuccessDeleteDataPasienBerobat(new ArrayList<>());
            }
        });
    }

    public void updateDataPasienBerobat (String kodeBerobat,
                                         String status,
                                         String noNik,
                                         String namaLengkap,
                                         String namaLayanan,
                                         String namaDokter,
                                         String tvValueTanggalberobat,
                                         String tvValueWaktuBerobat,
                                         String keluhanPasien) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().updateDataPasienBerobat(
                kodeBerobat,noNik,namaLayanan,namaDokter,tvValueTanggalberobat,tvValueWaktuBerobat,keluhanPasien,status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String usia = response.body().getUsia();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iKonfirmasiPasien.onSuccessUpdateDataPasien(dataPendaftaranEntityList,usia);
                    } else {
                        iKonfirmasiPasien.onSuccessUpdateDataPasien(new ArrayList<>(),"");
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {

                }

            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                iKonfirmasiPasien.onSuccessUpdateDataPasien(new ArrayList<>(),"");
            }
        });
    }

}
