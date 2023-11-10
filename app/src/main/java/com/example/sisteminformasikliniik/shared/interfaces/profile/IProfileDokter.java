package com.example.sisteminformasikliniik.shared.interfaces.profile;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;

import java.util.List;

public interface IProfileDokter {

    void onFailed(String message);
    void showDataDokter(List<DokterEntity> dokterEntityList);
    void onSuccessUpdateProfileDokter(List<DokterEntity> dokterEntityList);
}
