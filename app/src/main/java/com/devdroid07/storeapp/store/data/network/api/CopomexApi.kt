package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.BuildConfig
import com.devdroid07.storeapp.store.data.network.dto.apomex.CopomexResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CopomexApi {

    @GET("info_cp/{busqueda}?token=${BuildConfig.API_KEY_COPOMEX}")
    suspend fun getInfoByPostalCode(
        @Path("busqueda") codigoPostal: String
    ): Response<List<CopomexResponse>>
}