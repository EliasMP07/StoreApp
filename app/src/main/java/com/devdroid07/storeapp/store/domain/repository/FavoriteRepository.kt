package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {


    suspend fun addMyFavorite(productId: String): Result<String, DataError.Network>

    fun getMyFavorites(): Flow<Result<List<Product>, DataError.Network>>

    suspend fun removeProductFavorite(idProduct: String): EmptyResult<DataError.Network>
}