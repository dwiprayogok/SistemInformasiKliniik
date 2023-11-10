package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;

import java.util.List;

public interface IDataMasterLayanan {

    void onSuccessSaveData(List<DataPelayananEntity> dokterEntityList);
    void onSuccessUpdateData(List<DataPelayananEntity> dokterEntityList);
    void onFailed();
    void showData(List<DataPelayananEntity> dokterEntityList);
    void onSuccessDeleteData(List<DataPelayananEntity> dokterEntityList);
}
