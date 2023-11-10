package com.example.sisteminformasikliniik.DB.Pembayaran;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pembayaran")
public class PembayaranEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "kode_faktur")
    String kodeFaktur;

    @ColumnInfo(name = "kode_rekam")
    String kodeRekam;

    @ColumnInfo(name = "kode_berobat")
    String kodeBerobat;

    @ColumnInfo(name = "nama_pasien")
    String namaPasien;

    @ColumnInfo(name = "tanggal_periksa")
    String tanggalPeriksa;

    @ColumnInfo(name = "anamnesa")
    String anamnesa;

    @ColumnInfo(name = "nama_layanan")
    String namaLayanan;

    @ColumnInfo(name = "biaya_layanan")
    String biayaLayanan;

    @ColumnInfo(name = "total_biaya_layanan")
    String totalBiayaLayanan;

    @ColumnInfo(name = "nama_obat")
    String namaObat;

    @ColumnInfo(name = "harga_obat")
    String hargaObat;

    @ColumnInfo(name = "total_biaya_obat")
    String totalBiayaObat;

    @ColumnInfo(name = "total_biaya_berobat")
    String totalBiayaBerobat;

    @ColumnInfo(name = "keterangan")
    String keterangan;

    @ColumnInfo(name = "status")
    String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodeFaktur() {
        return kodeFaktur;
    }

    public void setKodeFaktur(String kodeFaktur) {
        this.kodeFaktur = kodeFaktur;
    }

    public String getKodeRekam() {
        return kodeRekam;
    }

    public void setKodeRekam(String kodeRekam) {
        this.kodeRekam = kodeRekam;
    }

    public String getKodeBerobat() {
        return kodeBerobat;
    }

    public void setKodeBerobat(String kodeBerobat) {
        this.kodeBerobat = kodeBerobat;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getTanggalPeriksa() {
        return tanggalPeriksa;
    }

    public void setTanggalPeriksa(String tanggalPeriksa) {
        this.tanggalPeriksa = tanggalPeriksa;
    }

    public String getAnamnesa() {
        return anamnesa;
    }

    public void setAnamnesa(String anamnesa) {
        this.anamnesa = anamnesa;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public String getBiayaLayanan() {
        return biayaLayanan;
    }

    public void setBiayaLayanan(String biayaLayanan) {
        this.biayaLayanan = biayaLayanan;
    }

    public String getTotalBiayaLayanan() {
        return totalBiayaLayanan;
    }

    public void setTotalBiayaLayanan(String totalBiayaLayanan) {
        this.totalBiayaLayanan = totalBiayaLayanan;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getHargaObat() {
        return hargaObat;
    }

    public void setHargaObat(String hargaObat) {
        this.hargaObat = hargaObat;
    }

    public String getTotalBiayaObat() {
        return totalBiayaObat;
    }

    public void setTotalBiayaObat(String totalBiayaObat) {
        this.totalBiayaObat = totalBiayaObat;
    }

    public String getTotalBiayaBerobat() {
        return totalBiayaBerobat;
    }

    public void setTotalBiayaBerobat(String totalBiayaBerobat) {
        this.totalBiayaBerobat = totalBiayaBerobat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
