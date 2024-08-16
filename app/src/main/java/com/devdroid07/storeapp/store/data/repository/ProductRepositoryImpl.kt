package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toBanner
import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.mappers.toReview
import com.devdroid07.storeapp.store.data.network.api.ProductApiService
import com.devdroid07.storeapp.store.data.network.dto.store.product.ReviewRequest
import com.devdroid07.storeapp.store.domain.model.Banner
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Review
import com.devdroid07.storeapp.store.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl(
    private val productApiService: ProductApiService,
    private val sessionStorage: SessionStorage,
) : ProductRepository {

    override fun getAllProduct(): Flow<Result<List<Product>, DataError.Network>> = flow {
        val result = safeCall {
            productApiService.getAllProducts(
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
        val result = safeCall {
            productApiService.getSingleProduct(
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

        val result = safeCall {
            productApiService.addReviewProduct(
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
        val result = safeCall {
            productApiService.getReviewProduct(
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

    override suspend fun searchProduct(query: String): Result<List<Product>, DataError.Network> {
        val result = safeCall {
            productApiService.searchProduct(
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

    override fun getAllBanner(): Flow<Result<List<Banner>, DataError.Network>> = flow {
        val result = safeCall {
            productApiService.getAllBanners()
        }
        when(result){
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(Result.Success(
                    result.data.data?.map {
                        it.toBanner()
                    }?: emptyList()
                ))
            }
        }
    }.flowOn(Dispatchers.IO)


}