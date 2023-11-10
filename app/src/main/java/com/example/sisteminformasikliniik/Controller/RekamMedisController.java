package com.example.sisteminformasikliniik.Controller;

import android.content.Context;
import android.widget.EditText;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.IRekamMedis;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekamMedisController {

    Context context;
    private IRekamMedis rekamMedis;
    private KlinikDatabase klinikDatabase;
    List<DokterEntity> dokterEntityList = new ArrayList<>();
    List<DataPelayananEntity> dataPelayananEntityList = new ArrayList<>();
    List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    List<UserEntity> userEntityList = new ArrayList<>();
    List<DataObatEntity> dataObatEntityList = new ArrayList<>();
    List<RekamMedisEntity> rekamMedisEntityList = new ArrayList<>();
    private LoadingDialog dialog;
    public void setKlinikDatabase(KlinikDatabase klinikDatabase) {
        this.klinikDatabase = klinikDatabase;
    }

    public RekamMedisController(Context context) {
        this.context = context;
    }


    public void setDialog(LoadingDialog dialog) {
        this.dialog = dialog;
    }

    public void setRekamMedis(IRekamMedis rekamMedis) {
        this.rekamMedis = rekamMedis;
    }

    public void clearField(EditText...edt) {
        for (EditText editText : edt) {
            if (!editText.getText().toString().isEmpty()) {
                editText.setText("");
            }
        }
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

    public List<RekamMedisEntity> rekamMedisEntityList(){
        List<RekamMedisEntity> list =  new ArrayList<>();
        list =  klinikDatabase.rekamMedisDao().select();
        return  list;
    }
    public void saveData(
            String noRekam, String noPelayanan, String nik, String namaPasien,
            String namaDokter, String jenisPelayanan, String diagnosa,
            String reseoPbat , String waktuPelayanan
    ) {
        RekamMedisEntity rekamMedisEntity = new RekamMedisEntity();
        rekamMedisEntity.setKodeBerobat("");
        rekamMedisEntity.setKodeRekam(noRekam);
        rekamMedisEntity.setNamaPasien(namaPasien);
        rekamMedisEntity.setUmur("");
        rekamMedisEntity.setNamaDokter(namaDokter);
        rekamMedisEntity.setAnamnesa(diagnosa);
        rekamMedisEntity.setTherapi(reseoPbat);
        rekamMedisEntity.setKeterangan("");
        klinikDatabase.rekamMedisDao().registerRekamMedis(rekamMedisEntity);
    }

    public List<DokterEntity> getALLDataDokter() {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataDokter();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        rekamMedis.showDataDokter(dokterEntityList);
                    } else {
                        rekamMedis.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                rekamMedis.onFailed(t.getMessage());
            }
        });
        return dokterEntityList;
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
                        rekamMedis.showDataLayanan(dataPelayananEntityList);
                    } else {
                        rekamMedis.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                rekamMedis.onFailed(t.getMessage());
            }
        });
        return dataPelayananEntityList;
    }

    public void setUpdateRekamMedis (
            String kodeBerobat, String kode_rekam, String nama_pasien, String umur, String tanggal_periksa, String nama_dokter, String pelayanan,
            String anamnesa, String obat, String therapi, String Keterangan) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getMasterDataService().UpdateRekamMedis(kodeBerobat,kode_rekam,nama_pasien,umur,
                tanggal_periksa,nama_dokter,pelayanan,anamnesa,obat,therapi,Keterangan,"Done");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    rekamMedisEntityList = response.body().getResultRekamMedis();
                    if (value.equals("1")) {
                        rekamMedis.onSuccessUpdateDataRekamMedisPasien(rekamMedisEntityList);
                    } else {
                        rekamMedis.onFailed(response.body().getMessage());
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
                rekamMedis.onFailed(t.getMessage());
            }
        });
    }

    public List<RekamMedisEntity> getALLDataRekamMedisPasien(String userName) {
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().AllDataRekamMedisByUsernameDokter(userName);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    rekamMedisEntityList = response.body().getResultRekamMedis();
                    if (value.equals("1")) {
                    rekamMedis.showAllDataRekamMedis(rekamMedisEntityList);
                    }else {
                        rekamMedis.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                rekamMedis.onFailed(t.getMessage());

            }
        });
        return rekamMedisEntityList;
    }

    public void deleteDataRekamMedisPasien(String kodeRekam) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().deleteDataRekamMedisPasien(kodeRekam);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    rekamMedisEntityList = response.body().getResultRekamMedis();
                    if (value.equals("1")) {
                        rekamMedis.onSuccessDeleteDataRekamMedisPasien(rekamMedisEntityList);
                    } else {
                        rekamMedis.onFailed(response.body().getMessage());
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
                rekamMedis.onFailed(t.getMessage());
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
                        rekamMedis.showDataObat(dataObatEntityList);
                    } else {
                        rekamMedis.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                rekamMedis.onFailed(t.getMessage());
            }
        });
        return dataObatEntityList;
    }
}
