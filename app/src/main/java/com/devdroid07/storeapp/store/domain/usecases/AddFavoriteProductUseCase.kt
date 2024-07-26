package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.repository.StoreRepository

class AddFavoriteProductUseCase(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(
        productId: String
    ): Result<String, DataError.Network> {
        return repository.addMyFavorite(
            productId = productId
        )
    }
}