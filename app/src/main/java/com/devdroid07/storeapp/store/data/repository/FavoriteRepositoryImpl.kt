package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.network.api.FavoriteApiService
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl (
    private val favoriteApiService: FavoriteApiService,
    private val sessionStorage: SessionStorage
): FavoriteRepository {

    override suspend fun addMyFavorite(productId: String): Result<String, DataError.Network> {
        val result = safeCall {
            favoriteApiService.addMyFavorites(
                idUser = sessionStorage.get()?.id.orEmpty(),
                idProduct = productId
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

    override fun getMyFavorites(): Flow<Result<List<Product>, DataError.Network>> = flow {
        val result = safeCall {
            favoriteApiService.getMyFavorites(
                idUser = sessionStorage.get()?.id.orEmpty()
            )
        }

        when (result) {
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(Result.Success(result.data.data?.map {
                    it.toProduct()
                }.orEmpty()))
            }
        }
    }

    override suspend fun removeProductFavorite(idProduct: String): EmptyResult<DataError.Network> {
        val result = safeCall {
            favoriteApiService.deleteProductMyFavorite(
                idUser = sessionStorage.get()?.id.orEmpty(),
                idProduct = idProduct
            )
        }
        return result.asEmptyDataResult()
    }
}