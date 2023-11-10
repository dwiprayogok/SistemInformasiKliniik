package com.example.sisteminformasikliniik.transfer.interfaces;

import com.example.sisteminformasikliniik.Model.ResponseError;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DaftarBerobatServices {

    @FormUrlEncoded
    @POST("/apiDaftarBerobat.php")
    Call<ResponseError> DaftarBerobat(
            @Field("kode_berobat") String kodeBerobat,
            @Field("nik") String nik,
            @Field("nama_layanan") String namaLayanan,
            @Field("nama_dokter") String namaDokter,
            @Field("tanggal_berobat") String tglBerobat,
            @Field("waktu_berobat") String waktuBerobat,
            @Field("keluhan") String keluhan,
            @Field("status") String status

    );


    @GET("/getDetailDataKartuBerobatUser.php")
    Call<ResponseError> getKartu(@Query("status") String status,
                                 @Query("nikPasien") String nikPasien);

    @GET("/getALLDataDaftarBerobat.php")
    Call<ResponseError> AllDataPasienDaftarBerobat();


    @GET("/deleteDataPasienBerobat.php")
    Call<ResponseError> deleteDataPasienBerobat(@Query("nik") String nik);


    @GET("/getALLDataDaftarBerobatNeedConfirmation.php")
    Call<ResponseError> AllDataPasienDaftarBerobatNeedConfirmation();


    @GET("/getAllDataPasienBerobatApproved.php")
    Call<ResponseError> getDataPasienApprove(@Query("status") String status );


    @FormUrlEncoded
    @POST("/updateDataPasienBerobat.php")
    Call<ResponseError> updateDataPasienBerobat(
            @Field("kode_berobat") String kodeBerobat,
            @Field("nik") String nik,
            @Field("nama_layanan") String namaLayanan,
            @Field("nama_dokter") String namaDokter,
            @Field("tanggal_berobat") String tglBerobat,
            @Field("waktu_berobat") String waktuBerobat,
            @Field("keluhan") String keluhan,
            @Field("status") String status);


    @FormUrlEncoded
    @POST("/apiTambahRekamMedisPasien.php")
    Call<ResponseError> TambahRekamMedis(
            @Field("kode_berobat") String kodeBerobat,
            @Field("kode_rekam") String kodeRekam,
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("tanggal_periksa") String tanggalPeriksa,
            @Field("nama_dokter") String namaDokter,
            @Field("anamnesa") String anamnesa,
            @Field("therapi") String therapi,
            @Field("keterangan") String keterangan,
            @Field("status") String status
    );

}
