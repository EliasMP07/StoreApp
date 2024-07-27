package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

   fun getAllProduct(): Flow<Result<List<Product>, DataError.Network>>

   suspend fun getSingleProduct(idProduct: String): Result<Product, DataError.Network>

   suspend fun addMyCart(productId: String, quantity: Int): Result<String, DataError.Network>

   fun getMyCart(): Flow<Result<List<Cart>, DataError.Network>>

   suspend fun removeProductCart(idProduct: Int): EmptyResult<DataError.Network>

   suspend fun addMyFavorite(productId: String): Result<String, DataError.Network>

   fun getMyFavorites(): Flow<Result<List<Product>, DataError.Network>>

   suspend fun removeProductFavorite(idProduct: String): EmptyResult<DataError.Network>

   suspend fun searchProduct(query: String): Result<List<Product>, DataError.Network>

}