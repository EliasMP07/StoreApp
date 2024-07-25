package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.StoreRepository

class RemoveProductMyCartUseCase(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(idProduct: Int): EmptyResult<DataError.Network> {
        return repository.removeProductCart(idProduct = idProduct)
    }
}