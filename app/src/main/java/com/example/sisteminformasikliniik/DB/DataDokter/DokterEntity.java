package com.example.sisteminformasikliniik.DB.DataDokter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "MasterDataDokter")
public class DokterEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id ;

    @ColumnInfo(name = "username")
    String username;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "NIK")
    String nikDokter;

    @ColumnInfo(name = "Nama_Dokter")
    String namaDokter;

    @ColumnInfo(name = "noHp_Dokter")
    String noHp;

    @ColumnInfo(name = "Alamat")
    String alamat;

    @ColumnInfo(name = "role")
    String role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNikDokter() {
        return nikDokter;
    }

    public void setNikDokter(String nikDokter) {
        this.nikDokter = nikDokter;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
