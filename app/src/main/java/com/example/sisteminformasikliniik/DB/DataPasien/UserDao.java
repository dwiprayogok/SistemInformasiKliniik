package com.example.sisteminformasikliniik.DB.DataPasien;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserEntity userEntity);

    @Query("Select * from DataPasien where username=(:username) and password=(:password)")
    UserEntity login(String username, String password);


    @Update
    void updateUser(UserEntity entity);

    // Mengambil data
    @Query("SELECT * FROM DataPasien where username=(:username)")
    List<UserEntity> select(String username);

    // Mengambil data
    @Query("SELECT * FROM DataPasien where nama=(:nama)")
    List<UserEntity> getProfile(String nama);

    @Query("DELETE FROM DataPasien WHERE nik=(:nik)")
    void delete(String nik);

}