package com.example.sisteminformasikliniik.shared.interfaces.profile;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;

import java.util.List;

public interface IProfile {
    void onFailed();
    void showData(List<UserEntity> userEntityList);
    void onSuccessUpdateProfile(List<UserEntity> userEntityList);

}
