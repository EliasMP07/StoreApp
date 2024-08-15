package com.devdroid07.storeapp.store.domain.usecases.cart

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.CartRepository

class UpdateQuantityProductUseCase(
    private val repository: CartRepository,
) {
    suspend operator fun invoke(
        productId: String,
        quantity: Int,
    ): EmptyResult<DataError.Network> {
        return repository.updateQuantity(
            productId = productId,
            quantity = quantity
        )
    }
}