package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toCart
import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.remote.StoreApiService
import com.devdroid07.storeapp.store.data.remote.dto.CartRequest
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.UnknownHostException

class StoreRepositoryImpl(
    private val api: StoreApiService,
    private val sessionStorage: SessionStorage
) : StoreRepository {

    override fun getAllProduct(): Flow<Result<List<Product>, DataError.Network>> = callbackFlow {
        val result = safeCall2 {
            api.getAllProducts(token = sessionStorage.get()?.token.orEmpty())
        }

        when (result) {
            is Result.Error -> {
                trySend(Result.Error(result.error))
            }
            is Result.Success -> {
                trySend(Result.Success(result.data.data?.map {
                    it.toProduct()
                }.orEmpty()))
            }
        }

        awaitClose {

        }
    }

    override suspend fun getSingleProduct(idProduct: String): Result<Product, DataError.Network> {
        val result = safeCall2 {
            api.getSingleProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                idProduct = idProduct
            )
        }
        return when(result){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data?.toProduct()?:Product())
            }
        }
    }

    override suspend fun addMyCart(
        productId: String,
        quantity: Int
    ): Result<String, DataError.Network> {
        val result = safeCall2 {
            api.addMyCart(
                token = sessionStorage.get()?.token.orEmpty(),
                cartRequest = CartRequest(
                    idUser = sessionStorage.get()?.id.orEmpty(),
                    productId = productId,
                    quantity = quantity
                )
            )
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data.orEmpty())
            }
        }
    }

    override fun getMyCart(): Flow<Result<List<Cart>, DataError.Network>> = callbackFlow {
        val result = safeCall2 {
            api.getMyCart(
                token = sessionStorage.get()?.token.orEmpty(),
                idUser = sessionStorage.get()?.id.orEmpty()
            )
        }

        when (result) {
            is Result.Error -> {
                trySend(Result.Error(result.error))
            }
            is Result.Success -> {
                trySend(Result.Success(result.data.data?.map {
                    it.toCart()
                }.orEmpty()))
            }
        }

        awaitClose {

        }
    }

}