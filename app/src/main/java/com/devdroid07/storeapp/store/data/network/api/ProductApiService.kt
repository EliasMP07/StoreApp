package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.BannerDto
import com.devdroid07.storeapp.store.data.network.dto.store.ProductDto
import com.devdroid07.storeapp.store.data.network.dto.store.ReviewDto
import com.devdroid07.storeapp.store.data.network.dto.store.ReviewRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("products/getAll/{id_user}")
    suspend fun getAllProducts(@Path("id_user") idUser: String): Response<StoreResponse<List<ProductDto>>>

    @GET("products/getAllBanners")
    suspend fun getAllBanners(): Response<StoreResponse<List<BannerDto>>>

    @GET("products/{id_product}")
    suspend fun getSingleProduct(@Path("id_product") idProduct: String): Response<StoreResponse<ProductDto>>

    @POST("products/addReview")
    suspend fun addReviewProduct(@Body reviewRequest: ReviewRequest): Response<StoreResponse<String>>

    @GET("products/getReviews/{product_id}")
    suspend fun getReviewProduct(@Path("product_id") productId: String): Response<StoreResponse<List<ReviewDto>>>

    @GET("products/search/{query}")
    suspend fun searchProduct(@Path("query") query: String): Response<StoreResponse<List<ProductDto>>>
}