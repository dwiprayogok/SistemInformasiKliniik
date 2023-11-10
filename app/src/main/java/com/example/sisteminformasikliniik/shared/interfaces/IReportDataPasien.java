package com.example.sisteminformasikliniik.shared.interfaces;

import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;

import java.util.List;

public interface IReportDataPasien {
    void onShowDataPasien(List<DataPendaftaranEntity> dataPendaftaranEntityList,List<DataLoginEntitiy> dataLoginEntitiys);
    void onShowDetailDataPasiem(List<UserEntity> userEntityList,List<DataLoginEntitiy> dataLoginEntitiys);
    void onShowDataRekamMedisPasien(List<RekamMedisEntity>rekamMedisEntityList,List<DataLoginEntitiy> dataLoginEntitiys);
    void onShowDataPembayaran(List<PembayaranEntity> pembayaranEntities,List<DataLoginEntitiy> dataLoginEntitiys);
}
