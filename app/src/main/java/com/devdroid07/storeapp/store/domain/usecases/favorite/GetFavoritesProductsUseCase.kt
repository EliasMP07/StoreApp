package com.devdroid07.storeapp.store.domain.usecases.favorite

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesProductsUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<Result<List<Product>, DataError.Network>> {
        return repository.getMyFavorites()
    }
}