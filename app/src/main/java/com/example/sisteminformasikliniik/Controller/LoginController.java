package com.example.sisteminformasikliniik.Controller;

import android.content.Context;
import android.widget.EditText;

import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.Model.ResponseError;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.interfaces.ILogin;
import com.example.sisteminformasikliniik.shared.interfaces.IRegister;
import com.example.sisteminformasikliniik.transfer.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {

    ILogin iLogin;
    IRegister iRegister;
    private KlinikDatabase klinikDatabase;
    private LoadingDialog dialog;

    List<UserEntity> userEntityList = new ArrayList<>();

    List<DataLoginEntitiy> dataLoginEntitiys = new ArrayList<>();

    public LoginController(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    public LoginController(IRegister iRegister) {
        this.iRegister = iRegister;
    }

    public void setDialog(LoadingDialog dialog) {
        this.dialog = dialog;
    }

    public void setKlinikDatabase(KlinikDatabase klinikDatabase) {
        this.klinikDatabase = klinikDatabase;
    }

    public boolean isSuccessLogin(Context context, String username, String password) {
        boolean isSuccess = true;
        if (username.isEmpty() && password.isEmpty()) {
            isSuccess = false;
            iLogin.onUserAndPasswordEmpty();
        } else if (password.length() < 6) {
            isSuccess = false;
            iLogin.onPasswordEmpty();
        } else if (!username.isEmpty() && !password.isEmpty()){
            isSuccess = true;
            iLogin.onSuccessCheckData();
        }
        return  isSuccess;
    }

    public boolean isSuccessRegister( EditText...edt) {
        for (EditText editText : edt) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Harap Di isi");
                iRegister.onFailed("Maaf, Silahkan isi semua data anda dengan benar!");
                return false;
            } else {
                iRegister.onSuccessCheckData();
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

    public void setLoginUser(String userName, String password) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getiRegistrasiService().getLogin(userName,password);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    //userEntityList = response.body().getResultUser();
                    dataLoginEntitiys = response.body().getResultLogin();
                    if (value.equals("1")) {
                        //iLogin.onSuccessLogin(userEntityList);
                        iLogin.onSuccessLogin(dataLoginEntitiys);
                    } else {
                        iLogin.onFailedLogin();
                    }
                }catch (NullPointerException e){
                    iLogin.onFailedLogin();
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iLogin.onFailedLogin();
            }
        });
    }

    public void saveDataRegister(String username,String password,String gender, String nik, String nama,
                                 String usia, String statusKerja, String noHP, String alamat){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        userEntity.setPassword(password);
        userEntity.setGender(gender);
        userEntity.setNik(nik);
        userEntity.setNama(nama);
        userEntity.setUsia(usia);
        userEntity.setPekerjaan(statusKerja);
        userEntity.setNoHp(noHP);
        userEntity.setAlamat(alamat);
        if (userEntity!=null) {
            klinikDatabase.userDao().registerUser(userEntity);
            iRegister.onSuccessRegister();
        } else {
            iRegister.onFailed("Maaf, Silahkan isi semua data anda dengan benar!");
        }
    }

    public void setRegister (String username,String password,String gender, String nik, String nama,
                             String usia, String statusKerja, String noHP, String alamat) {
        dialog.show();
        Call<ResponseError> call = ApiClient.getInstance().getiRegistrasiService().registrasi(username,password,nik,nama,gender,usia,alamat,statusKerja,noHP);
        call.enqueue(new Callback<ResponseError>() {
            @Override
            public void onResponse(Call<ResponseError> call, Response<ResponseError> response) {
                try {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        saveDataRegister(username,password,gender,nik,nama,usia,statusKerja,noHP,alamat);
                    } else {
                        iRegister.onFailed(message);
                    }
                }catch (NullPointerException e){
                    iRegister.onFailed(e.getMessage());
                }finally {
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseError> call, Throwable t) {
                dialog.dismiss();
                iRegister.onFailed(t.getMessage());
            }
        });
    }


}
