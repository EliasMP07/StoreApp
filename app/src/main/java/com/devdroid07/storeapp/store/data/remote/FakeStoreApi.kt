package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/{idProduct}")
    suspend fun getSingleProduct(@Path("idProduct") idProduct: String): ProductDto

    companion object{
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}