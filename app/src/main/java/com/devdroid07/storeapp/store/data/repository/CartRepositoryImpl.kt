package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toCart
import com.devdroid07.storeapp.store.data.network.api.CartApiService
import com.devdroid07.storeapp.store.data.network.dto.store.CartRequest
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryImpl(
    private val cartApiService: CartApiService,
    private val sessionStorage: SessionStorage,
) : CartRepository {
    override suspend fun addMyCart(
        productId: String,
        quantity: Int,
    ): Result<String, DataError.Network> {
        val result = safeCall {
            cartApiService.addMyCart(
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

    override fun getMyCart(): Flow<Result<List<Cart>, DataError.Network>> = flow {
        val result = safeCall {
            cartApiService.getMyCart(
                idUser = sessionStorage.get()?.id.orEmpty()
            )
        }

        when (result) {
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(Result.Success(result.data.data?.map {
                    it.toCart()
                }.orEmpty()))
            }
        }
    }

    override suspend fun removeProductCart(idProduct: Int): EmptyResult<DataError.Network> {
        val result = safeCall {
            cartApiService.removeProductMyCart(
                idUser = sessionStorage.get()?.id.orEmpty(),
                idProduct = idProduct.toString()
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun updateQuantity(
        productId: String,
        quantity: Int,
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            cartApiService.updateQuantity(
                cartRequest = CartRequest(
                    idUser = sessionStorage.get()?.id.orEmpty(),
                    productId = productId,
                    quantity = quantity
                )
            )
        }
        return result.asEmptyDataResult()
    }
}