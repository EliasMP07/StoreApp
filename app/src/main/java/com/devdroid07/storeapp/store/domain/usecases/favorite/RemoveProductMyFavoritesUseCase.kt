package com.devdroid07.storeapp.store.domain.usecases.favorite

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.FavoriteRepository
import com.devdroid07.storeapp.store.domain.repository.ProductRepository

class RemoveProductMyFavoritesUseCase (
    private val repository: FavoriteRepository
){
    suspend operator fun invoke(idProduct: String): EmptyResult<DataError.Network> {
        return repository.removeProductFavorite(idProduct)
    }
}
