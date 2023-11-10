package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;

import java.util.List;

public interface IDataMasterDokter {
    void onSuccessSaveData(List<DokterEntity>dokterEntityList);
    void onSuccessUpdateData(List<DokterEntity> dokterEntityList);
    void onFailed();
    void showData(List<DokterEntity> dokterEntityList);
    void onSuccessDeleteData(List<DokterEntity> dokterEntityList);
}
