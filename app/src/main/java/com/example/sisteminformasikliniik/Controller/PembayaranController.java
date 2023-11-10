package com.example.sisteminformasikliniik.Controller;

import android.content.Context;
import android.widget.EditText;

import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.Admin.IPembayaran;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranController {

    private Context mContext;
    private LoadingDialog dialog;
    List<DataPelayananEntity> dataPelayananEntityList = new ArrayList<>();
    List<PembayaranEntity> pembayaranEntityList = new ArrayList<>();
    List<DataObatEntity> obatEntities = new ArrayList<>();
    private IPembayaran pembayaran;
    List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();

    public PembayaranController(Context mContext, LoadingDialog dialog) {
        this.mContext = mContext;
        this.dialog = dialog;
    }

    public void setPembayaran(IPembayaran pembayaran) {
        this.pembayaran = pembayaran;
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


    public List<DataPelayananEntity> getAllLayanan() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataLayanan();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPelayananEntityList = response.body().getResultLayanan();
                    if (value.equals("1")) {
                            pembayaran.showData(dataPelayananEntityList);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                pembayaran.onFailed(t.getMessage());
            }
        });
        return dataPelayananEntityList;
    }

    public List<DataObatEntity> getALLObat() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataObat();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    obatEntities = response.body().getResultObat();
                    if (value.equals("1")) {
                        pembayaran.showDataObat(obatEntities);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                pembayaran.onFailed(t.getMessage());
            }
        });
        return obatEntities;
    }


    public List<PembayaranEntity> getAllDataPembayaran() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataPembayaran();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    pembayaranEntityList = response.body().getResultPembayaran();
                    if (value.equals("1")) {
                        pembayaran.checkDataPembayaran(pembayaranEntityList);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    pembayaran.onFailed( e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                pembayaran.onFailed(t.getMessage());
            }
        });
        return pembayaranEntityList;
    }

    public List<RekamMedisEntity> getALLDataRekamMedisPasien(String status) {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataRekamMedis(status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    rekamMedisEntityList = response.body().getResultRekamMedis();
                    if (value.equals("1")) {
                        pembayaran.showAllDataRekamMedis(rekamMedisEntityList);
                    }else {
                        pembayaran.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                pembayaran.onFailed(t.getMessage());

            }
        });
        return rekamMedisEntityList;
    }

    public void setTambahPembayaran (
            String noFaktur ,
            String kodeRekam ,
            String namaPasien ,
            String tanggalPeriksa ,
            String anamnesa ,
            String namaLayanan,
            String biayaPelayanan ,
            String totalBiayaLayanan ,
            String namaObat,
            String hargaObat ,
            String totalBiayaObat,
            String totalBiayaBerobat ,
            String keterangan,
            String status
    ) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().
                TambahPembayaran(
                        noFaktur ,
                        kodeRekam ,
                        namaPasien ,
                        tanggalPeriksa ,
                        anamnesa ,
                        namaLayanan,
                        biayaPelayanan ,
                        totalBiayaLayanan ,
                        namaObat,
                        hargaObat ,
                        totalBiayaObat,
                        totalBiayaBerobat ,
                        keterangan,
                        status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    pembayaranEntityList = response.body().getResultPembayaran();
                    if (value.equals("1")) {
                        pembayaran.onSuccessAddPembayaran(pembayaranEntityList);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
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
                pembayaran.onFailed(t.getMessage());
            }
        });
    }

    public void setUpdatePembayaran (
            String noFaktur ,
            String kodeRekam ,
            String namaPasien ,
            String tanggalPeriksa ,
            String anamnesa ,
            String namaLayanan,
            String biayaPelayanan ,
            String totalBiayaLayanan ,
            String namaObat,
            String hargaObat ,
            String totalBiayaObat,
            String totalBiayaBerobat ,
            String keterangan,
            String status

            ) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().updatePembayaran(
                noFaktur ,
                kodeRekam ,
                namaPasien ,
                tanggalPeriksa ,
                anamnesa ,
                namaLayanan,
                biayaPelayanan ,
                totalBiayaLayanan ,
                namaObat,
                hargaObat ,
                totalBiayaObat,
                totalBiayaBerobat ,
                keterangan,
                status
        );
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    pembayaranEntityList = response.body().getResultPembayaran();
                    if (value.equals("1")) {
                        pembayaran.onSuccessUpdatePembayaran(pembayaranEntityList);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
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
                pembayaran.onFailed(t.getMessage());
            }
        });
    }


    public void deleteDataPembayaran(String kodeFaktur) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataPembayaran(kodeFaktur);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    pembayaranEntityList = response.body().getResultPembayaran();
                    if (value.equals("1")) {
                    pembayaran.onSuccessDeletePembayaran(pembayaranEntityList);
                    } else {
                        pembayaran.onFailed(response.body().getMessage());
                    }
                }catch (NullPointerException e) {
                    e.getMessage();
                    pembayaran.onFailed(e.getMessage());
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.printStackTrace();
                pembayaran.onFailed(t.getMessage());
            }
        });
    }

}
