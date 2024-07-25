package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.auth.data.remote.response.StoreResponse
import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StoreApiService {

    @GET("products/getAll")
    suspend fun getAllProducts(@Header("Authorization") token: String): StoreResponse<List<ProductDto>>

    @GET("products/{id_product}")
    suspend fun getSingleProduct(@Header("Authorization") token: String, @Path("id_product") idProduct: String): StoreResponse<ProductDto>

}