package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.ProductDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoriteApiService {
    @POST("favorite/add")
    suspend fun addMyFavorites(@Query("id_user") idUser: String, @Query("id_product") idProduct: String): Response<StoreResponse<String>>

    @GET("favorite/getFavorites/{id_user}")
    suspend fun getMyFavorites(@Path("id_user") idUser: String): Response<StoreResponse<List<ProductDto>>>

    @DELETE("favorite/delete")
    suspend fun deleteProductMyFavorite(@Query("id_user") idUser: String, @Query("id_product") idProduct: String): Response<StoreResponse<String>>
}
