package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.cart.CartDto
import com.devdroid07.storeapp.store.data.network.dto.store.cart.CartRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiService {
    @POST("cart/insert")
    suspend fun addMyCart(@Body cartRequest: CartRequest): Response<StoreResponse<String>>

    @GET("cart/getMyCart/{id_user}")
    suspend fun getMyCart(@Path("id_user") idUser: String): Response<StoreResponse<List<CartDto>>>

    @DELETE("cart/deleteProduct/remove")
    suspend fun removeProductMyCart(
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String,
    ): Response<StoreResponse<String>>

    @PUT("/v1/cart/updateQuantity")
    suspend fun updateQuantity(@Body cartRequest: CartRequest): Response<StoreResponse<String>>
}