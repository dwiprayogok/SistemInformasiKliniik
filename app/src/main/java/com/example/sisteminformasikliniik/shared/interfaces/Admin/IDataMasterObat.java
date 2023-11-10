package com.example.sisteminformasikliniik.shared.interfaces.Admin;


import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;

import java.util.List;

public interface IDataMasterObat {

    void onSuccessSaveDataObat(List<DataObatEntity> dataObatEntityList);
    void onSuccessUpdateDataObat(List<DataObatEntity> dataObatEntityList);
    void onFailed();
    void showData(List<DataObatEntity> dataObatEntityList);
    void onSuccessDeleteData(List<DataObatEntity> dataObatEntityList);

}
