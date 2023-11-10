package com.example.sisteminformasikliniik.DB.DataPendaftaran;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;

import java.util.List;

@Dao
public interface PendaftaranDao {

    @Insert
    void tambahPendaftaran(DataPendaftaranEntity pendaftaranEntity);

    // Menghapus data
    @Delete
    void delete(DataPendaftaranEntity pendaftaranEntity);

    // Mengupdate data
    @Update
    void update(DataPendaftaranEntity pendaftaranEntity);

    @Query("SELECT * FROM pendaftaran_berobat where status IN (:status) ORDER BY kode_berobat ASC")
    List<DataPendaftaranEntity> selectByStatus(String status);


    @Query("SELECT * FROM pendaftaran_berobat  ORDER BY kode_berobat ASC")
    List<DataPendaftaranEntity> select();
}
