package com.example.sisteminformasikliniik.DB.DataPendaftaran;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pendaftaran_berobat")
public class DataPendaftaranEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "kode_berobat")
    String kodeBerobat;

    @ColumnInfo(name = "nik")
    String nik;

    @ColumnInfo(name = "nama_pasien")
    String namaPasien;

    @ColumnInfo(name = "jenis_pelayanan")
    String jenisPelayanan;

    @ColumnInfo(name = "nama_dokter")
    String namaDokter;

    @ColumnInfo(name = "tanggal_berobat")
    String tanggalBerobat;

    @ColumnInfo(name = "waktu_berobat")
    String waktuPelayanan;

    @ColumnInfo(name = "keluhan")
    String keluhan;

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

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getJenisPelayanan() {
        return jenisPelayanan;
    }

    public void setJenisPelayanan(String jenisPelayanan) {
        this.jenisPelayanan = jenisPelayanan;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getTanggalBerobat() {
        return tanggalBerobat;
    }

    public void setTanggalBerobat(String tanggalBerobat) {
        this.tanggalBerobat = tanggalBerobat;
    }

    public String getWaktuPelayanan() {
        return waktuPelayanan;
    }

    public void setWaktuPelayanan(String waktuPelayanan) {
        this.waktuPelayanan = waktuPelayanan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
