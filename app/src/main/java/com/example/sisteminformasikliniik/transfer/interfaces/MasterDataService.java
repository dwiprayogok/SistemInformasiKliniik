package com.example.sisteminformasikliniik.transfer.interfaces;

import com.example.sisteminformasikliniik.Model.ResponseError;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MasterDataService {
    @FormUrlEncoded
    @POST("/apiTambahDokter.php")
    Call<ResponseError> TambahDokter(
            @Field("username") String username,
            @Field("password") String password,
            @Field("NIK") String nik,
            @Field("Nama_Dokter") String nama,
            @Field("noHp_Dokter") String noHp,
            @Field("Alamat") String alamat,
            @Field("role") String role);



    @FormUrlEncoded
    @POST("/updateDataDokter.php")
    Call<ResponseError> updateDokter(
            @Field("username") String username,
            @Field("password") String password,
            @Field("NIK") String nik,
            @Field("Nama_Dokter") String nama,
            @Field("noHp_Dokter") String noHp,
            @Field("Alamat") String alamat,
            @Field("role") String role);

    @GET("/getDataDokter.php")
    Call<ResponseError> getDataDokter(@Query("username") String username);

    @GET("/getALLDataDokter.php")
    Call<ResponseError> AllDataDokter();

    @FormUrlEncoded
    @POST("/deleteDataDokter.php")
    Call<ResponseError> deleteDataDokter(@Field("NIK") String NIK);


    @FormUrlEncoded
    @POST("/apiTambahLayanan.php")
    Call<ResponseError> TambahLayanan(
            @Field("kode_pelayanan") String kodePelayanan,
            @Field("nama_pelayanan") String namaPelayanan,
            @Field("biaya_pelayanan") String biayaLayanan);


    @FormUrlEncoded
    @POST("/updateDataLayanan.php")
    Call<ResponseError> UpdateLayanan(
            @Field("kode_pelayanan") String kodePelayanan,
            @Field("nama_pelayanan") String namaPelayanan,
            @Field("biaya_pelayanan") String biayaLayanan);


    @GET("/getALLDataLayanan.php")
    Call<ResponseError> AllDataLayanan();

    @FormUrlEncoded
    @POST("/deleteDataLayanan.php")
    Call<ResponseError> deleteDataLayanan(@Field("kode_pelayanan") String kodePelayanan);





    @FormUrlEncoded
    @POST("/apiTambahObat.php")
    Call<ResponseError> TambahObat(
            @Field("kode_obat") String kodeObat,
            @Field("nama_obat") String namaObat,
            @Field("harga_obat") String hargaObat
        );


    @FormUrlEncoded
    @POST("/updateDataObat.php")
    Call<ResponseError> UpdateObat(
            @Field("kode_obat") String kodeObat,
            @Field("nama_obat") String namaObat,
            @Field("harga_obat") String hargaObat
    );

    @GET("/getALLDataObat.php")
    Call<ResponseError> AllDataObat();


    @FormUrlEncoded
    @POST("/deleteDataObat.php")
    Call<ResponseError> deleteDataObat(@Field("kode_obat") String kodeObat);



    @FormUrlEncoded
    @POST("/apiTambahPemilik.php")
    Call<ResponseError> TambahPemilik(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama_pemilik") String nama,
            @Field("noHp_pemilik") String noHp,
            @Field("alamat") String alamat,
            @Field("role") String role);


    @FormUrlEncoded
    @POST("/updateDataPemilik.php")
    Call<ResponseError> UpdatePemilik(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nik") String nik,
            @Field("nama_pemilik") String nama,
            @Field("noHp_pemilik") String noHp,
            @Field("alamat") String alamat,
            @Field("role") String role);


    @GET("/getALLDataPemilik.php")
    Call<ResponseError> AllDataPemilik();



    @GET("/getDataPemilik.php")
    Call<ResponseError> getDataPemilik(@Query("username") String username);


    @FormUrlEncoded
    @POST("/deleteDataPemilik.php")
    Call<ResponseError> deleteDataPemilik(@Field("nik") String nik);


    @GET("/getDataUserByNIk.php")
    Call<ResponseError> getDataUserByNik(@Query("nik") String nik);

    @FormUrlEncoded
    @POST("/deleteDataPasien.php")
    Call<ResponseError> deleteDataPasien(@Field("nik") String nik);


    @FormUrlEncoded
    @POST("/deleteDataPembayaran.php")
    Call<ResponseError> deleteDataPembayaran(@Field("kode_faktur") String kodeFaktur);


    @FormUrlEncoded
    @POST("/getHargaLayanan.php")
    Call<ResponseError> getHargaLayanan(@Query("kode_pelayanan") String kodeLayanan);


    @FormUrlEncoded
    @POST("/apiTambahRekamMedisPasien.php")
    Call<ResponseError> TambahRekamMedis(
            @Field("kode_berobat") String kodeBerobat,
            @Field("kode_rekam") String kodeRekam,
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("tanggal_periksa") String tanggalPeriksa,
            @Field("nama_dokter") String namaDokter,
            @Field("pelayanan") String pelayanan,
            @Field("anamnesa") String anamnesa,
            @Field("obat") String obat,
            @Field("therapi") String therapi,
            @Field("keterangan") String keterangan);


    @FormUrlEncoded
    @POST("/UpdateRekamMedisPasien.php")
    Call<ResponseError> UpdateRekamMedis(
            @Field("kode_berobat") String kodeBerobat,
            @Field("kode_rekam") String kodeRekam,
            @Field("nama") String nama,
            @Field("umur") String umur,
            @Field("tanggal_periksa") String tanggalPeriksa,
            @Field("nama_dokter") String namaDokter,
            @Field("pelayanan") String pelayanan,
            @Field("anamnesa") String anamnesa,
            @Field("obat") String obat,
            @Field("therapi") String therapi,
            @Field("keterangan") String keterangan,
            @Field("status") String status);


    //@GET("/getALLDataRekamMedisPasien.php")
    @GET("/getALLDataRekamMedisPasienNew.php")
    Call<ResponseError> AllDataRekamMedis(@Query("status") String status);

    @GET("/getALLDataRekamMedisPasienByUsernameDokter.php")
    Call<ResponseError> AllDataRekamMedisByUsernameDokter(@Query("username") String userName);


    @FormUrlEncoded
    @POST("/deleteRekamMedisPasienByKodeRekam.php")
    Call<ResponseError> deleteDataRekamMedisPasien(@Query("kode_rekam") String kodeRekam);



    //@GET("/getAllDataPembayaran.php")
    @GET("/getAllDataPembayaranNew.php")
    Call<ResponseError> AllDataPembayaran();


    @FormUrlEncoded
    @POST("/apiTambahDataPembayaran.php")
    Call<ResponseError> TambahPembayaran(
            @Field("kode_faktur") String noFaktur,
            @Field("kode_rekam") String kodeRekam,
            @Field("nama_pasien") String namaPasien,
            @Field("tanggal_periksa") String tanggalPeriksa,
            @Field("anamnesa") String anamnesa,
            @Field("nama_layanan") String namaLayanan,
            @Field("biaya_layanan") String biayaPelayanan,
            @Field("total_biaya_layanan") String totalBiayaLayanan,
            @Field("nama_obat") String namaObat,
            @Field("harga_obat") String hargaObat,
            @Field("total_biaya_obat") String totalBiayaObat,
            @Field("total_biaya_berobat") String totalBiayaBerobat,
            @Field("keterangan") String keterangan,
            @Field("status") String status
            );



    @FormUrlEncoded
    @POST("/updateDataPembayaran.php")
    Call<ResponseError> updatePembayaran(
            @Field("kode_faktur") String noFaktur,
            @Field("kode_rekam") String kodeRekam,
            @Field("nama_pasien") String namaPasien,
            @Field("tanggal_periksa") String tanggalPeriksa,
            @Field("anamnesa") String anamnesa,
            @Field("nama_layanan") String namaLayanan,
            @Field("biaya_layanan") String biayaPelayanan,
            @Field("total_biaya_layanan") String totalBiayaLayanan,
            @Field("nama_obat") String namaObat,
            @Field("harga_obat") String hargaObat,
            @Field("total_biaya_obat") String totalBiayaObat,
            @Field("total_biaya_berobat") String totalBiayaBerobat,
            @Field("keterangan") String keterangan,
            @Field("status") String status
    );

}

