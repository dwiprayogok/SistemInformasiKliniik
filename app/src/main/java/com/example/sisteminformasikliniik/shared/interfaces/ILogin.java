package com.example.sisteminformasikliniik.shared.interfaces;

import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;

import java.util.List;

public interface ILogin {
    void onUserAndPasswordEmpty();
    void onPasswordEmpty();
    void onSuccessCheckData();
    void onSuccessLogin(List<DataLoginEntitiy> dataLoginEntitiyList);
    void onFailedLogin();
}
