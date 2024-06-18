package com.devdroid07.storeapp.store.data.remote

import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import retrofit2.http.GET

interface FakeStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    companion object{
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}