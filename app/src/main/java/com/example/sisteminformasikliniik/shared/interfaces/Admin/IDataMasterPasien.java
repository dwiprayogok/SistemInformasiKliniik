package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;

import java.util.List;

public interface IDataMasterPasien {
    void onDetailDataPasien(List<UserEntity> userEntityList);
    void onSuccessDeleteDataPasien(List<UserEntity> dataPendaftaranEntityList);
    void onSuccessUpdateDataPasien(List<UserEntity> userEntityList);
}
