package com.example.sisteminformasikliniik.Model;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;

import java.util.List;

public class ResponseError {

    String value;
    String message;
    String usia;
    List<DokterEntity> resultDokter;

    List<DataObatEntity> resultObat;

    List<DataPelayananEntity> resultLayanan;

    List<DataPemilikEntity> resultPemilik;

    List<DataPendaftaranEntity> resultDaftarBerobat;

    List<UserEntity> resultUser;

    List<PembayaranEntity> resultPembayaran;

    List<RekamMedisEntity> resultRekamMedis;

    List<KartuBerobat> resultKartuBerobat;

    List<DataLoginEntitiy> resultLogin;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<DokterEntity> getResultDokter() {
        return resultDokter;
    }

    public List<DataObatEntity> getResultObat() {
        return resultObat;
    }

    public List<DataPelayananEntity> getResultLayanan() {
        return resultLayanan;
    }

    public List<DataPemilikEntity> getResultPemilik() {
        return resultPemilik;
    }

    public List<DataPendaftaranEntity> getResultDaftarBerobat() {
        return resultDaftarBerobat;
    }

    public List<UserEntity> getResultUser() {
        return resultUser;
    }

    public List<DataLoginEntitiy> getResultLogin() {
        return resultLogin;
    }

    public String getUsia() {
        return usia;
    }

    public List<PembayaranEntity> getResultPembayaran() {
        return resultPembayaran;
    }

    public List<RekamMedisEntity> getResultRekamMedis() {
        return resultRekamMedis;
    }

    public List<KartuBerobat> getResultKartuBerobat() {
        return resultKartuBerobat;
    }
}
