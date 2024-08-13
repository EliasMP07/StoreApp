package com.devdroid07.storeapp.store.domain.usecases.cart

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.repository.CartRepository

class AddMyCartUseCase(
    private val repository: CartRepository
) {
    suspend operator fun invoke(
        productId: String,
        quantity: Int
    ): Result<String, DataError.Network> {
        return repository.addMyCart(
            productId = productId,
            quantity = quantity
        )
    }
}