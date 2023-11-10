package com.example.sisteminformasikliniik.Model;

import java.io.Serializable;

public class KartuBerobat implements Serializable {

    private String kodeBerobat;
    private String nik;
    private String kodeRekam;
    private String status;
    private String namaLengkapPasien;
    private String umurPasien;
    private String tanggalBerobat;
    private String namaDokter;
    private String anamnesa;
    private String therapi;
    private String keterangan;

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKodeBerobat() {
        return kodeBerobat;
    }

    public void setKodeBerobat(String kodeBerobat) {
        this.kodeBerobat = kodeBerobat;
    }

    public String getKodeRekam() {
        return kodeRekam;
    }

    public void setKodeRekam(String kodeRekam) {
        this.kodeRekam = kodeRekam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaLengkapPasien() {
        return namaLengkapPasien;
    }

    public void setNamaLengkapPasien(String namaLengkapPasien) {
        this.namaLengkapPasien = namaLengkapPasien;
    }

    public String getUmurPasien() {
        return umurPasien;
    }

    public void setUmurPasien(String umurPasien) {
        this.umurPasien = umurPasien;
    }

    public String getTanggalBerobat() {
        return tanggalBerobat;
    }

    public void setTanggalBerobat(String tanggalBerobat) {
        this.tanggalBerobat = tanggalBerobat;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getAnamnesa() {
        return anamnesa;
    }

    public void setAnamnesa(String anamnesa) {
        this.anamnesa = anamnesa;
    }

    public String getTherapi() {
        return therapi;
    }

    public void setTherapi(String therapi) {
        this.therapi= therapi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
