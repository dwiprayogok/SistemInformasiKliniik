package com.example.sisteminformasikliniik.DB.Pembayaran;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;

import java.util.List;

@Dao
public interface PembayaranDao {

    @Insert
    void tambahDataPembayaran(PembayaranEntity pembayaranEntity);

    // Mengupdate data
    @Update
    void update(PembayaranEntity pembayaranEntity);

    // Mengambil data
    @Query("SELECT * FROM pembayaran ORDER BY nama_pasien ASC")
    List<PembayaranEntity> select();
}
