package com.example.sisteminformasikliniik.DB.DataPemilik;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PemilikDao {
    @Insert
    void TambahDataPemilik(DataPemilikEntity entity);

    @Delete
    void delete(DataPemilikEntity entity);

    @Update
    void update(DataPemilikEntity dataPemilikEntity);

   @Query("SELECT * FROM MasterDataPemilik ORDER BY nama_pemilik ASC")
    List<DataPemilikEntity> select();
}