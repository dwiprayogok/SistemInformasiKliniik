package com.example.sisteminformasikliniik.transfer.interfaces;

import com.example.sisteminformasikliniik.Model.ResponseError;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReportServices {

    @GET("/getReportALLDataPasien_BU.php")
    Call<ResponseError> AllReportDataPasien();

    @GET("/getAllDataPasienBerobatApproved_BU.php")
    Call<ResponseError> getDataPasienApproved(@Query("status") String status );

}
