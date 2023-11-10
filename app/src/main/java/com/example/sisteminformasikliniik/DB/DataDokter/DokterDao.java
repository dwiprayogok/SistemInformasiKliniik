package com.example.sisteminformasikliniik.DB.DataDokter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DokterDao {
    @Insert
    void tambahDataDokter(DokterEntity dokterEntity);

    @Delete
    void delete(DokterEntity dokterEntity);

    @Update
    void update(DokterEntity dokterEntity);

    @Query("SELECT * FROM MasterDataDokter ORDER BY Nama_Dokter ASC")
    List<DokterEntity> select();

}
