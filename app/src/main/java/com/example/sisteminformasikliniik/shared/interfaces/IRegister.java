package com.example.sisteminformasikliniik.shared.interfaces;

public interface IRegister {
    void onFailed(String error);
    void onSuccessCheckData();
    void onSuccessRegister();
}
