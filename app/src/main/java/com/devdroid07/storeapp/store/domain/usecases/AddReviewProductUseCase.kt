package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.StoreRepository

class AddReviewProductUseCase(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(
        productId: String,
        rating: Double,
        comment: String?
    ): EmptyResult<DataError.Network> {
        return repository.addReviewProduct(
            productId,
            rating,
            comment
        )
    }
}