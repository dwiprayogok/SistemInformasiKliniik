package com.example.sisteminformasikliniik.DB.DataPelayanan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;

import java.util.List;


@Dao
public interface PelayananDao {

    @Insert
    void tambahDataPelayanan(DataPelayananEntity dataPelayananEntity);

    // Menghapus data
    @Delete
    void delete(DataPelayananEntity dataPelayananEntity);

    // Mengupdate data
    @Update
    void update(DataPelayananEntity dataPelayananEntity);

    @Query("SELECT * FROM DataMasterPelayanan ORDER BY nama_pelayanan ASC")
    List<DataPelayananEntity> select();
}
