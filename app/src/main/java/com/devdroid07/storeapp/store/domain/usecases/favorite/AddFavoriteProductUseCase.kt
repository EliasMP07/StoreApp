package com.devdroid07.storeapp.store.domain.usecases.favorite

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.repository.FavoriteRepository
import com.devdroid07.storeapp.store.domain.repository.ProductRepository

class AddFavoriteProductUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(
        productId: String
    ): Result<String, DataError.Network> {
        return repository.addMyFavorite(
            productId = productId
        )
    }
}