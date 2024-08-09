package com.devdroid07.storeapp.store.domain.usecases.cart

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.repository.CartRepository
import com.devdroid07.storeapp.store.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetMyCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<Result<List<Cart>, DataError.Network>>{
        return repository.getMyCart()
    }
}