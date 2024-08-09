package com.devdroid07.storeapp.store.domain.usecases.product

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.ProductRepository

class RemoveProductMyFavoritesUseCase (
    private val repository: ProductRepository
){
    suspend operator fun invoke(idProduct: String): EmptyResult<DataError.Network> {
        return repository.removeProductFavorite(idProduct)
    }
}
