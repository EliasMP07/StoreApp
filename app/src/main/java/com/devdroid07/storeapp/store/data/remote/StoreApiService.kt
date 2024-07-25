package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.store.data.remote.dto.CartDto
import com.devdroid07.storeapp.auth.data.remote.response.StoreResponse
import com.devdroid07.storeapp.store.data.remote.dto.CartRequest
import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StoreApiService {

    @GET("products/getAll")
    suspend fun getAllProducts(@Header("Authorization") token: String): Response<StoreResponse<List<ProductDto>>>

    @GET("products/{id_product}")
    suspend fun getSingleProduct(
        @Header("Authorization") token: String,
        @Path("id_product") idProduct: String
    ): Response<StoreResponse<ProductDto>>

    @GET("cart/insert")
    suspend fun addMyCart(
        @Header("Authorization") token: String,
        @Body cartRequest: CartRequest
    ): Response<StoreResponse<String>>

    @GET("cart/getMyCart/{id_user}")
    suspend fun getMyCart(
        @Header("Authorization") token: String,
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<CartDto>>>
}