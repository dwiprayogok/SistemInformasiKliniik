package com.example.sisteminformasikliniik.shared.interfaces.profile;

import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;

import java.util.List;

public interface IProfileOwner {

    void onFailed(String message);
    void showDataOwner(List<DataPemilikEntity> dataPemilikEntityList);
    void onSuccessUpdateDataOwner(List<DataPemilikEntity> dataPemilikEntityList);
}
