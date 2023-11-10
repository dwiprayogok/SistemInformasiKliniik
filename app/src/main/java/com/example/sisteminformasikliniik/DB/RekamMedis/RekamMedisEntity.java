package com.example.sisteminformasikliniik.DB.RekamMedis;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "rekam_medis")
public class RekamMedisEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "kode_berobat")
    String kodeBerobat;

    @ColumnInfo(name = "kode_rekam")
    String kodeRekam;

    @ColumnInfo(name = "nama_pasien")
    String namaPasien;

    @ColumnInfo(name = "umur")
    String umur;

    @ColumnInfo(name = "tanggal_periksa")
    String tanggalPeriksa;

    @ColumnInfo(name = "nama_dokter")
    String namaDokter;

    @ColumnInfo(name = "pelayanan")
    String pelayanan;

    @ColumnInfo(name = "anamnesa")
    String anamnesa;

    @ColumnInfo(name = "obat")
    String obat;

    @ColumnInfo(name = "therapi")
    String therapi;

    @ColumnInfo(name = "Keterangan")
    String Keterangan;

    @ColumnInfo(name = "status")
    String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getTanggalPeriksa() {
        return tanggalPeriksa;
    }

    public void setTanggalPeriksa(String tanggalPeriksa) {
        this.tanggalPeriksa = tanggalPeriksa;
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
        this.therapi = therapi;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(String pelayanan) {
        this.pelayanan = pelayanan;
    }

    public String getObat() {
        return obat;
    }

    public void setObat(String obat) {
        this.obat = obat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
