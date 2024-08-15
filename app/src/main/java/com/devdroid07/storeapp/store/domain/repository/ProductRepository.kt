package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Banner
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProduct(): Flow<Result<List<Product>, DataError.Network>>

    suspend fun getSingleProduct(idProduct: String): Result<Product, DataError.Network>

    suspend fun addReviewProduct(
        productId: String,
        rating: Double,
        comment: String?,
    ): EmptyResult<DataError.Network>

    fun getReviewsProduct(productId: String): Flow<Result<List<Review>, DataError.Network>>

    suspend fun searchProduct(query: String): Result<List<Product>, DataError.Network>

    fun getAllBanner(): Flow<Result<List<Banner>, DataError.Network>>

}