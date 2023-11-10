package com.example.sisteminformasikliniik.DB.DataLogin;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;

import java.util.List;

@Dao
public interface DataLoginDao {

    @Update
    void  update(DataLoginEntitiy dataLoginEntitiy);

    @Query("SELECT * FROM DataLogin ORDER BY nama ASC")
    List<DataLoginEntitiy> select();

}
