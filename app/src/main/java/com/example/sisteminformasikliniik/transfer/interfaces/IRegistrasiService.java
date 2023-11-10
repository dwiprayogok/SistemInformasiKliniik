package com.example.sisteminformasikliniik.transfer.interfaces;

import com.example.sisteminformasikliniik.Model.ResponseError;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRegistrasiService {

    @FormUrlEncoded
    @POST("/apiRegistrasi.php")
    Call<ResponseError> registrasi(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama") String nama,
            @Field("gender") String gender,
            @Field("usia") String usia,
            @Field("alamat") String alamat,
            @Field("pekerjaan") String pekerjaan,
            @Field("no_hp") String noHp);

    @FormUrlEncoded
    @POST("/updateDataUser.php")
    Call<ResponseError> updateProfile(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama") String nama,
            @Field("gender") String gender,
            @Field("usia") String usia,
            @Field("alamat") String alamat,
            @Field("pekerjaan") String pekerjaan,
            @Field("no_hp") String noHp,
            @Field("role") String role);


    @FormUrlEncoded
    @POST("/updateDataProfileDokter.php")
    Call<ResponseError> updateProfileDokter(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama") String nama,
            @Field("no_hp") String noHp,
            @Field("alamat") String alamat,
            @Field("role") String role);


    @FormUrlEncoded
    @POST("/updateDataProfileOwner.php")
    Call<ResponseError> updateProfileOwner(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama_pemilik") String nama,
            @Field("alamat") String alamat,
            @Field("noHp_pemilik") String noHp);



    @GET("/getDataUser.php")
    Call<ResponseError> getDataUser(@Query("username") String username);



    @GET("/getDataUserByNIk.php")
    Call<ResponseError> getDataUserByUsername(@Query("username") String username);


    @GET("/getLogin.php")
    Call<ResponseError> getLogin(@Query("username") String username,@Query("password") String password);
}
