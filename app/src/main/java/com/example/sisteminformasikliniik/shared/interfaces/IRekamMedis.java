package com.example.sisteminformasikliniik.shared.interfaces;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;

import java.util.List;

public interface IRekamMedis {

    void showDataDokter(List<DokterEntity> dokterEntityList);
    void showDataLayanan(List<DataPelayananEntity> dataPelayananEntityList);
    void showDataObat(List<DataObatEntity> dataPelayananEntityList);
    void showAllDataRekamMedis(List<RekamMedisEntity> rekamMedisEntityList);
    void onSuccessUpdateDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList);
    void onSuccessDeleteDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList);
    void onFailed(String error);
}
