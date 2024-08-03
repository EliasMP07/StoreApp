package com.devdroid07.storeapp.store.data.remote.api

import com.devdroid07.storeapp.auth.data.remote.response.StoreResponse
import com.devdroid07.storeapp.store.data.remote.dto.AddressDto
import com.devdroid07.storeapp.store.data.remote.dto.AddressRequest
import com.devdroid07.storeapp.store.data.remote.dto.CartDto
import com.devdroid07.storeapp.store.data.remote.dto.CartRequest
import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import com.devdroid07.storeapp.store.data.remote.dto.ReviewDto
import com.devdroid07.storeapp.store.data.remote.dto.ReviewRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApiService {

    @GET("products/getAll/{id_user}")
    suspend fun getAllProducts(
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<ProductDto>>>

    @GET("products/{id_product}")
    suspend fun getSingleProduct(
        @Path("id_product") idProduct: String
    ): Response<StoreResponse<ProductDto>>

    @POST("products/addReview")
    suspend fun addReviewProduct(
        @Body reviewRequest: ReviewRequest
    ): Response<StoreResponse<String>>

    @GET("products/getReviews/{product_id}")
    suspend fun getReviewProduct(
        @Path("product_id") productId: String
    ): Response<StoreResponse<List<ReviewDto>>>

    @POST("cart/insert")
    suspend fun addMyCart(
        @Body cartRequest: CartRequest
    ): Response<StoreResponse<String>>

    @GET("cart/getMyCart/{id_user}")
    suspend fun getMyCart(
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<CartDto>>>

    @DELETE("cart/deleteProduct/remove")
    suspend fun removeProductMyCart(
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>

    @POST("favorite/add")
    suspend fun addMyFavorites(
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>

    @GET("favorite/getFavorites/{id_user}")
    suspend fun getMyFavorites(
        @Path("id_user") idUser: String
    ): Response<StoreResponse<List<ProductDto>>>

    @DELETE("favorite/delete")
    suspend fun deleteProductMyFavorite(
        @Query("id_user") idUser: String,
        @Query("id_product") idProduct: String
    ): Response<StoreResponse<String>>

    @GET("products/search/{query}")
    suspend fun searchProduct(
        @Path("query") query: String
    ): Response<StoreResponse<List<ProductDto>>>

    @GET("address/getAllAddress/{id_user}")
    suspend fun getAllMyAddress(
        @Path("id_user") userId: String
    ): Response<StoreResponse<List<AddressDto>>>

    @POST("address/create")
    suspend fun createAddress(
        @Body addressRequest: AddressRequest
    ): Response<StoreResponse<AddressDto>>

    @DELETE("address/delete")
    suspend fun deleteAddress(
        @Query("address_id") addressId: String,
    ): Response<StoreResponse<String>>

}