package com.example.sisteminformasikliniik.Controller;

import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.IReportDataPasien;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportController {
    IReportDataPasien reportDataPasien;
    private LoadingDialog dialog;
    private List<UserEntity> userEntityList = new ArrayList<>();
    private List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    private List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();
    private List<PembayaranEntity> pembayaranEntityList = new ArrayList<>();
    private List<DataLoginEntitiy> dataLoginEntitiys = new ArrayList<>();

    public ReportController(IReportDataPasien reportDataPasien, LoadingDialog dialog) {
        this.reportDataPasien = reportDataPasien;
        this.dialog = dialog;
    }

    public List<UserEntity> getAllDataPasien() {
        Call<ResponseError> call = ApiClient.getInstance().getReportServices().AllReportDataPasien();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    dataLoginEntitiys = response.body().getResultLogin();
                    if (value.equals("1")) {
                        reportDataPasien.onShowDetailDataPasiem(userEntityList,dataLoginEntitiys);
                    } else {
                        reportDataPasien.onShowDetailDataPasiem(new ArrayList<>(), new ArrayList<>());
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                    reportDataPasien.onShowDetailDataPasiem(new ArrayList<>(),new ArrayList<>() );
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                reportDataPasien.onShowDetailDataPasiem(new ArrayList<>(),new ArrayList<>() );
            }
        });
        return userEntityList;
    }


    public List<DataPendaftaranEntity> getAllDataPasienApproved(String status) {
        Call<ResponseError> call = ApiClient.getInstance().getReportServices().getDataPasienApproved(status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String usia = response.body().getUsia();
                    dataLoginEntitiys = response.body().getResultLogin();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        reportDataPasien.onShowDataPasien(dataPendaftaranEntityList,dataLoginEntitiys);
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



    public List<RekamMedisEntity> getALLDataRekamMedisPasien(String status) {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataRekamMedis(status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    rekamMedisEntityList = response.body().getResultRekamMedis();
                    dataLoginEntitiys = response.body().getResultLogin();
                    if (value.equals("1")) {
                        reportDataPasien.onShowDataRekamMedisPasien(rekamMedisEntityList,dataLoginEntitiys );
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {

            }
        });
        return rekamMedisEntityList;
    }

    public List<PembayaranEntity> getALLDataPembayaran() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataPembayaran();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    pembayaranEntityList = response.body().getResultPembayaran();
                    dataLoginEntitiys = response.body().getResultLogin();
                    if (value.equals("1")) {
                        reportDataPasien.onShowDataPembayaran(pembayaranEntityList,dataLoginEntitiys);
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {

            }
        });
        return pembayaranEntityList;
    }

}
