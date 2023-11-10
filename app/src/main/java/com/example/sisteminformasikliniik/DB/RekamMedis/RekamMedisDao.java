package com.example.sisteminformasikliniik.DB.RekamMedis;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RekamMedisDao {

    @Insert
    void registerRekamMedis(RekamMedisEntity rekamMedisEntity);

    // Mengupdate data
    @Update
    void update(RekamMedisEntity rekamMedisEntity);

    // Mengambil data
    @Query("SELECT * FROM rekam_medis ORDER BY nama_pasien ASC")
    List<RekamMedisEntity> select();

    @Delete
    void  delete(RekamMedisEntity entity);
}
