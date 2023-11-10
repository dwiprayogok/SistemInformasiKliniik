package com.example.sisteminformasikliniik.Controller;

import android.content.Context;
import android.widget.EditText;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.Model.KartuBerobat;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.IKartuBerobat;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfile;
import com.example.sisteminformasikliniik.shared.interfaces.User.IDaftarPelayanan;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfileDokter;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfileOwner;
import com.example.sisteminformasikliniik.transfer.ApiClient;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileDokter;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileOwner;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileUser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserController {

    IProfile iProfile;
    IProfileDokter iProfileDokter;
    IProfileOwner profileOwner;
    IKartuBerobat iKartuBerobat;
    IDaftarPelayanan iDaftarPelayanan;
    private KlinikDatabase klinikDatabase;
    private LoadingDialog dialog;
    List<DataPemilikEntity> dataPemilikEntityList = new ArrayList<>();
    List<DokterEntity> dokterEntityList = new ArrayList<>();
    List<DataPelayananEntity> dataPelayananEntityList = new ArrayList<>();
    List<DataPendaftaranEntity> dataPendaftaranEntityList = new ArrayList<>();
    List<UserEntity> userEntityList = new ArrayList<>();
    List<KartuBerobat> kartuBerobatList = new ArrayList<>();
    Context mContext;


    public UserController(Context context) {
        this.mContext = context;
    }

    public void setDialog(LoadingDialog dialog) {
        this.dialog = dialog;
    }

    public void setKlinikDatabase(KlinikDatabase klinikDatabase) {
        this.klinikDatabase = klinikDatabase;
    }

    public void setiKartuBerobat(IKartuBerobat iKartuBerobat) {
        this.iKartuBerobat = iKartuBerobat;
    }

    public void setProfileOwner(IProfileOwner profileOwner) {
        this.profileOwner = profileOwner;
    }

    public void setiProfileDokter(IProfileDokter iProfileDokter) {
        this.iProfileDokter = iProfileDokter;
    }

    public void setiProfile(IProfile iProfile) {
        this.iProfile = iProfile;
    }

    public void setiDaftarPelayanan(IDaftarPelayanan iDaftarPelayanan) {
        this.iDaftarPelayanan = iDaftarPelayanan;
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


    public void UpdateProfile( int idUser,String username,String password,String gender, String nik, String nama,
                              String usia, String statusKerja, String noHP, String alamat  ) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        userEntity.setUserName(username);
        userEntity.setPassword(password);
        userEntity.setGender(gender);
        userEntity.setNik(nik);
        userEntity.setNama(nama);
        userEntity.setUsia(usia);
        userEntity.setPekerjaan(statusKerja);
        userEntity.setNoHp(noHP);
        userEntity.setAlamat(alamat);
        if (userEntity != null) {
            klinikDatabase.userDao().updateUser(userEntity);
        } else {
            iProfile.onFailed();
        }
    }


    public void saveDaftarPelayanan(String noPelayanan, String nik,String tanggalBerobat,
                                    String waktu, String keluhan, String namaDokter, String daftarPelayanan){
        DataPendaftaranEntity dataPendaftaranEntity = new DataPendaftaranEntity();
        dataPendaftaranEntity.setKodeBerobat(noPelayanan);
        dataPendaftaranEntity.setNik(nik);
        dataPendaftaranEntity.setNamaPasien("");
        dataPendaftaranEntity.setJenisPelayanan(daftarPelayanan);
        dataPendaftaranEntity.setNamaDokter(namaDokter);
        dataPendaftaranEntity.setTanggalBerobat(tanggalBerobat);
        dataPendaftaranEntity.setWaktuPelayanan(waktu);
        dataPendaftaranEntity.setKeluhan(keluhan);
        dataPendaftaranEntity.setStatus("Waiting");

        if (dataPendaftaranEntity == null){
            iDaftarPelayanan.onFailedDaftar();
        } else {
            klinikDatabase.pendaftaranDao().tambahPendaftaran(dataPendaftaranEntity);
            iDaftarPelayanan.onSuccessDaftar();
        }
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
                        iDaftarPelayanan.showDataDokter(dokterEntityList);
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
                        iDaftarPelayanan.showDataLayanan(dataPelayananEntityList);
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
        return dataPelayananEntityList;
    }


    public void setDaftarBerobat (String kodeBerobat, String nik, String namaLayanan,
                                  String namaDokter, String tglBerobat, String waktuBerobat,
                                  String keluhan, String status) {
        //dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getDaftarBerobatServices().DaftarBerobat(kodeBerobat,nik,namaLayanan,namaDokter,
                tglBerobat,waktuBerobat,keluhan,status);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        iDaftarPelayanan.onSuccessDaftar();
                    } else {
                        iDaftarPelayanan.onFailedDaftar();
                    }
                }catch (NullPointerException e){
                    e.getMessage();
                }
                finally {
                    //dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                //dialog.dismiss();
                iDaftarPelayanan.onFailedDaftar();

            }
        });
    }


    public void getDataUser(String userName) {
        Call<ResponseError> call = ApiClient.getInstance().getiRegistrasiService().getDataUser(userName);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                    iProfile.showData(userEntityList);
                    } else {
                        iProfile.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }finally {

                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                iProfile.onFailed();

            }
        });
    }


    public void getDataUserByUsername(String userName) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getiRegistrasiService().getDataUserByUsername(userName);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        iProfile.showData(userEntityList);
                    } else {
                        iProfile.onFailed();
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                iProfile.onFailed();
                dialog.dismiss();
            }
        });
    }


    public void setUpdateProfile(String username,String password,String gender, String nik, String nama,
                                  String usia, String statusKerja, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getiRegistrasiService().updateProfile(username,password,nik,nama,gender,usia,alamat,statusKerja,noHP,"Pasien");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    userEntityList = response.body().getResultUser();
                    if (value.equals("1")) {
                        iProfile.onSuccessUpdateProfile(userEntityList);
                        EventBus.getDefault().post(new EventbusProfileUser(userEntityList,response.body().getMessage()));
                    } else {
                        iProfile.onFailed();
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
                iProfile.onFailed();
            }
        });
    }



    public void getDataDokterByUsername(String userName) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().getDataDokter(userName);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    if (value.equals("1")) {
                        dokterEntityList = response.body().getResultDokter();
                        iProfileDokter.showDataDokter(dokterEntityList);
                    } else {
                        iProfileDokter.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    iProfileDokter.onFailed(e.getMessage());
                }
                finally {
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iProfileDokter.onFailed(t.getMessage());
            }
        });
    }

    public void setUpdateProfileDokter(String username,String password, String nik, String nama, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getiRegistrasiService().updateProfileDokter(username,password,nik,nama,noHP,alamat,"Dokter");
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dokterEntityList = response.body().getResultDokter();
                    if (value.equals("1")) {
                        iProfileDokter.onSuccessUpdateProfileDokter(dokterEntityList);
                        EventBus.getDefault().post(new EventbusProfileDokter(dokterEntityList,response.body().getMessage()));
                    } else {
                        iProfileDokter.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    iProfileDokter.onFailed(e.getMessage());
                }
                finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iProfileDokter.onFailed(t.getMessage());
            }
        });
    }




    public void getDataOwnerByUsername(String userName) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getMasterDataService().getDataPemilik(userName);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        profileOwner.showDataOwner(dataPemilikEntityList);
                    } else {
                        profileOwner.onFailed(response.body().getMessage());
                    }

                }catch (NullPointerException e){
                    profileOwner.onFailed(e.getMessage());
                }finally {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                profileOwner.onFailed(t.getMessage());
                dialog.dismiss();
            }
        });
    }

    public void setUpdateProfileOwner(String username,String password,String nik, String nama,
                                      String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().
                getiRegistrasiService().updateProfileOwner(username,password,nik,nama,alamat,noHP);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPemilikEntityList = response.body().getResultPemilik();
                    if (value.equals("1")) {
                        profileOwner.onSuccessUpdateDataOwner(dataPemilikEntityList);
                        EventBus.getDefault().post(new EventbusProfileOwner(dataPemilikEntityList,response.body().getMessage()));
                    } else {
                        iProfile.onFailed();
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
                iProfile.onFailed();
            }
        });
    }


    public List<DataPendaftaranEntity> getAllPasien() {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().AllDataPasienDaftarBerobat();
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    dataPendaftaranEntityList = response.body().getResultDaftarBerobat();
                    if (value.equals("1")) {
                        iDaftarPelayanan.onShowDataPasien(dataPendaftaranEntityList);
                    } else {
                        iDaftarPelayanan.onShowDataPasien(new ArrayList<>());
                    }

                }catch (NullPointerException e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                iDaftarPelayanan.onShowDataPasien(new ArrayList<>());

            }
        });

        return dataPendaftaranEntityList;
    }


    public  List<KartuBerobat> getKartuBerobat(String status, String nikPasien) {
        Call<ResponseError> call = ApiClient.getInstance().getDaftarBerobatServices().getKartu(status,nikPasien);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    kartuBerobatList = response.body().getResultKartuBerobat();
                    if (value.equals("1")) {
                        iKartuBerobat.showDetail(kartuBerobatList);
                    } else {
                        iKartuBerobat.onFailedShowData(response.body().getMessage());
                    }

                }catch (NullPointerException e) {
                    iKartuBerobat.onFailedShowData(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                t.getMessage();
                iKartuBerobat.onFailedShowData(t.getMessage());

            }
        });

        return kartuBerobatList;
    }

}
