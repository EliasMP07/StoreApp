package com.devdroid07.storeapp.store.domain.usecases.cart

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.CartRepository
import com.devdroid07.storeapp.store.domain.repository.ProductRepository

class RemoveProductMyCartUseCase(
    private val repository: CartRepository
) {
    suspend operator fun invoke(idProduct: Int): EmptyResult<DataError.Network> {
        return repository.removeProductCart(idProduct = idProduct)
    }
}