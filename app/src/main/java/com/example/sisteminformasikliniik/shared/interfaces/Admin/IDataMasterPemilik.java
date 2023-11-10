package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;

import java.util.List;

public interface IDataMasterPemilik {

    void onSuccessSaveData(List<DataPemilikEntity> dataPemilikEntityList);
    void onSuccessUpdateData(List<DataPemilikEntity> dataPemilikEntityList);
    void onFailed(String message);
    void showData(List<DataPemilikEntity> dataPemilikEntityList);
    void onSuccessDeleteData(List<DataPemilikEntity> dataPemilikEntityList);

}
