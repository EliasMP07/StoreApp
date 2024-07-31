package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.auth.data.remote.response.StoreResponse
import com.devdroid07.storeapp.store.data.remote.dto.CartDto
import com.devdroid07.storeapp.store.data.remote.dto.CartRequest
import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import com.devdroid07.storeapp.store.data.remote.dto.ReviewDto
import com.devdroid07.storeapp.store.data.remote.dto.ReviewRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApiService {

    @GET("products/getAll/{id_user}")
    suspend fun getAllProducts(
        @Header("Authorization") token: String,
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<ProductDto>>>

    @GET("products/{id_product}")
    suspend fun getSingleProduct(
        @Header("Authorization") token: String,
        @Path("id_product") idProduct: String
    ): Response<StoreResponse<ProductDto>>

    @POST("products/addReview")
    suspend fun addReviewProduct(
        @Header("Authorization") token: String,
        @Body reviewRequest: ReviewRequest
    ): Response<StoreResponse<String>>


    @GET("products/getReviews/{product_id}")
    suspend fun getReviewProduct(
        @Header("Authorization") token: String,
        @Path("product_id") productId: String
    ): Response<StoreResponse<List<ReviewDto>>>

    @POST("cart/insert")
    suspend fun addMyCart(
        @Header("Authorization") token: String,
        @Body cartRequest: CartRequest
    ): Response<StoreResponse<String>>

    @GET("cart/getMyCart/{id_user}")
    suspend fun getMyCart(
        @Header("Authorization") token: String,
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<CartDto>>>

    @DELETE("cart/deleteProduct/remove")
    suspend fun removeProductMyCart(
        @Header("Authorization") token: String,
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>


    @POST("favorite/insert")
    suspend fun addMyFavorites(
        @Header("Authorization") token: String,
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>

    @GET("favorite/getFavorites/{id_user}")
    suspend fun getMyFavorites(
        @Header("Authorization") token: String,
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<ProductDto>>>

    @DELETE("favorite/remove")
    suspend fun removeProductMyFavorite(
        @Header("Authorization") token: String,
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>


    @GET("products/search/{query}")
    suspend fun searchProduct(
        @Header("Authorization") token: String,
        @Path("query") query: String
    ): Response<StoreResponse<List<ProductDto>>>


}