package com.example.sisteminformasikliniik.shared.interfaces.User;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;

import java.util.List;

public interface IDaftarPelayanan {

    void onSuccessDaftar();
    void onFailedDaftar();
    void showDataDokter(List<DokterEntity> dokterEntityList);
    void showDataLayanan(List<DataPelayananEntity> dataPelayananEntityList);
    void onShowDataPasien(List<DataPendaftaranEntity> pendaftaranEntities);
}
