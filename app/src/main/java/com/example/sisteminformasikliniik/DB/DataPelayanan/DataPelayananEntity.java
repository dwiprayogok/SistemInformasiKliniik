package com.example.sisteminformasikliniik.DB.DataPelayanan;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "DataMasterPelayanan")
public class DataPelayananEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "kode_pelayanan")
    String kodePelayanan;

    @ColumnInfo(name = "nama_pelayanan")
    String namaPelayanan;

    @ColumnInfo(name = "biaya_pelayanan")
    String biayaPelayanan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodePelayanan() {
        return kodePelayanan;
    }

    public void setKodePelayanan(String kodePelayanan) {
        this.kodePelayanan = kodePelayanan;
    }

    public String getNamaPelayanan() {
        return namaPelayanan;
    }

    public void setNamaPelayanan(String namaPelayanan) {
        this.namaPelayanan = namaPelayanan;
    }

    public String getBiayaPelayanan() {
        return biayaPelayanan;
    }

    public void setBiayaPelayanan(String biayaPelayanan) {
        this.biayaPelayanan = biayaPelayanan;
    }
}
