package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.store.data.remote.dto.apomex.CopomexResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CopomexApi {

    @GET("info_cp/{busqueda}")
    suspend fun getInfoByPostalCode(
        @Path("busqueda") codigoPostal: String,
        @Query("token") token: String = "pruebas"
    ): Response<List<CopomexResponse>>
}