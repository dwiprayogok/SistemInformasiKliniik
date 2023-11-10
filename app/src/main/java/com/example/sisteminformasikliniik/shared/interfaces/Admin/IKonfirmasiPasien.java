package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;

import java.util.List;

public interface IKonfirmasiPasien {
    void onShowData(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia);
    void onSuccessSaveDataRekamMedisPasien(List<RekamMedisEntity> rekamMedisEntityList);
    void onRefusedDataRekamMedisPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList);
    void onSuccessUpdateDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList, String usia);
    void onSuccessDeleteDataPasienBerobat(List<DataPendaftaranEntity> dataPendaftaranEntityList);
}
