package com.devdroid07.storeapp.store.domain.usecases.order

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Order
import com.devdroid07.storeapp.store.domain.repository.OrderRepository

class GetSingleOrderUseCase (
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: String): Result<Order, DataError.Network>{
        return repository.getSingleOrder(orderId)
    }
}