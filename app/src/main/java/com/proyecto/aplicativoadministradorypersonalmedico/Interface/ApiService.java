package com.proyecto.aplicativoadministradorypersonalmedico.Interface;

import com.proyecto.aplicativoadministradorypersonalmedico.DB.DniResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v2/reniec/dni")
    Call<DniResponse> getDniInfo(
            @Header("Authorization") String authHeader,
            @Query("numero") String numero
    );
}
