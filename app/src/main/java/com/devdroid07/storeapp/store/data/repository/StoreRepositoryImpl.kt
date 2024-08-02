package com.devdroid07.storeapp.store.data.repository

import android.util.Log
import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toCart
import com.devdroid07.storeapp.store.data.mappers.toPostalCode
import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.mappers.toReview
import com.devdroid07.storeapp.store.data.remote.CopomexApi
import com.devdroid07.storeapp.store.data.remote.StoreApiService
import com.devdroid07.storeapp.store.data.remote.dto.CartRequest
import com.devdroid07.storeapp.store.data.remote.dto.ReviewRequest
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.model.PostalCode
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Review
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StoreRepositoryImpl(
    private val api: StoreApiService,
    private val copomexApi: CopomexApi,
    private val sessionStorage: SessionStorage,
) : StoreRepository {

    override fun getAllProduct(): Flow<Result<List<Product>, DataError.Network>> = flow {
        val result = safeCall2 {
            api.getAllProducts(
                token = sessionStorage.get()?.token.orEmpty(),
                idUser = sessionStorage.get()?.id.orEmpty(),
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
    }.flowOn(Dispatchers.IO)

    override suspend fun getSingleProduct(idProduct: String): Result<Product, DataError.Network> {
        val result = safeCall2 {
            api.getSingleProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                idProduct = idProduct
            )
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data?.toProduct() ?: Product())
            }
        }
    }

    override suspend fun addReviewProduct(
        productId: String,
        rating: Double,
        comment: String?,
    ): EmptyResult<DataError.Network> {

        val result = safeCall2 {
            api.addReviewProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                reviewRequest = ReviewRequest(
                    productId = productId,
                    rating = rating,
                    comment = comment,
                    userId = sessionStorage.get()?.id.orEmpty(),
                )
            )
        }

        return result.asEmptyDataResult()
    }

    override fun getReviewsProduct(productId: String): Flow<Result<List<Review>, DataError.Network>> = flow {
        val result = safeCall2 {
            api.getReviewProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                productId = productId,
            )

        }
        when (result) {
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(Result.Success(result.data.data?.map {
                    it.toReview()
                }.orEmpty()))
            }
        }
    }

    override suspend fun addMyCart(
        productId: String,
        quantity: Int,
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

    override fun getMyCart(): Flow<Result<List<Cart>, DataError.Network>> = flow {
        val result = safeCall2 {
            api.getMyCart(
                token = sessionStorage.get()?.token.orEmpty(),
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
        val result = safeCall2 {
            api.removeProductMyCart(
                token = sessionStorage.get()?.token.orEmpty(),
                idUser = sessionStorage.get()?.id.orEmpty(),
                idProduct = idProduct.toString()
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun addMyFavorite(productId: String): Result<String, DataError.Network> {
        val result = safeCall2 {
            api.addMyFavorites(
                token = sessionStorage.get()?.token.orEmpty(),
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
        val result = safeCall2 {
            api.getMyFavorites(
                token = sessionStorage.get()?.token.orEmpty(),
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
        val result = safeCall2 {
            api.removeProductMyFavorite(
                token = sessionStorage.get()?.token.orEmpty(),
                idUser = sessionStorage.get()?.id.orEmpty(),
                idProduct = idProduct
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun searchProduct(query: String): Result<List<Product>, DataError.Network> {
        val result = safeCall2 {
            api.searchProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                query = query
            )
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(
                    result.error
                )
            }
            is Result.Success -> {
                val products = result.data.data?.map {
                    it.toProduct()
                }
                Result.Success(
                    products ?: emptyList()
                )
            }
        }
    }

    override suspend fun getInfoByPostalCode(postalCode: String):Flow<Result<List<PostalCode>, DataError.Network>> = flow{
        val result = safeCall2 {
            copomexApi.getInfoByPostalCode(codigoPostal = postalCode, token = "pruebas")
        }
        when (result) {
            is Result.Error -> {
                emit(Result.Error(
                    result.error
                ))
            }
            is Result.Success -> {
                emit(Result.Success(
                    result.data.map {
                        it.postalCodeDto?.toPostalCode()?:PostalCode()
                    }
                ))
            }
        }
    }

}