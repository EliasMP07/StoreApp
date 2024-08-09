package com.devdroid07.storeapp.store.domain.usecases.product

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Review
import com.devdroid07.storeapp.store.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetReviewsProductUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<Result<List<Review>, DataError.Network>> {
        return repository.getReviewsProduct(productId = productId)
    }
}