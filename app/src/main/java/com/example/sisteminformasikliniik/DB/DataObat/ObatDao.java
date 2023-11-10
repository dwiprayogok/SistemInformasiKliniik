package com.example.sisteminformasikliniik.DB.DataObat;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;

import java.util.List;

@Dao
public interface ObatDao {

    @Insert
    void tambahDataObat(DataObatEntity obatEntity);

    // Menghapus data
    @Delete
    void delete(DataObatEntity dataObatEntity);

    // Mengupdate data
    @Update
    void update(DataObatEntity dataObatEntity);

    // Mengambil data
    @Query("SELECT * FROM DataMasterObat ORDER BY nama_obat ASC")
    List<DataObatEntity> select();
}
