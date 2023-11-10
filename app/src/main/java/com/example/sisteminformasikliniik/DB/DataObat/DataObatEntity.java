package com.example.sisteminformasikliniik.DB.DataObat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "DataMasterObat")
public class DataObatEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "kode_obat")
    String kodeObat;

    @ColumnInfo(name = "nama_obat")
    String namaObat;

    @ColumnInfo(name = "harga_obat")
    String hargaObat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
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
}
