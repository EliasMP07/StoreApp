package com.devdroid07.storeapp.store.domain.usecases.order

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Order
import com.devdroid07.storeapp.store.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetAllOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<Result<List<Order>, DataError.Network>>{
        return repository.getAllOrders()
    }
}