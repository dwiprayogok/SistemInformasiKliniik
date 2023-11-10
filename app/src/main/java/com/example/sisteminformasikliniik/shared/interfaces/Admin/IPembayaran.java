package com.example.sisteminformasikliniik.shared.interfaces.Admin;

import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;

import java.util.List;

public interface IPembayaran {
    void onFailed(String error);
    void showData(List<DataPelayananEntity> dataPelayananEntityList);
    void showDataObat(List<DataObatEntity> dataPelayananEntityList);
    void checkDataPembayaran(List<PembayaranEntity> pembayaranEntityList);
    void onSuccessAddPembayaran(List<PembayaranEntity> pembayaranEntityList);
    void onSuccessUpdatePembayaran(List<PembayaranEntity> pembayaranEntityList);
    void showAllDataRekamMedis(List<RekamMedisEntity> rekamMedisEntityList);
    void onSuccessDeletePembayaran(List<PembayaranEntity> pembayaranEntityList);
}
