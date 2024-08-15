package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addMyCart(
        productId: String,
        quantity: Int,
    ): Result<String, DataError.Network>

    fun getMyCart(): Flow<Result<List<Cart>, DataError.Network>>

    suspend fun removeProductCart(idProduct: Int): EmptyResult<DataError.Network>

    suspend fun updateQuantity(
        productId: String,
        quantity: Int,
    ): EmptyResult<DataError.Network>

}