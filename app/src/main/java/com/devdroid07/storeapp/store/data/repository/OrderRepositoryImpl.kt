package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.data.mappers.toOrder
import com.devdroid07.storeapp.store.data.network.api.OrderApiService
import com.devdroid07.storeapp.store.domain.model.Order
import com.devdroid07.storeapp.store.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val  orderApiService: OrderApiService
): OrderRepository {
    override fun getAllOrders(): Flow<Result<List<Order>, DataError.Network>> = flow{
        val result = safeCall {
            orderApiService.getAllOrders(
                userId = sessionStorage.get()?.id.orEmpty()
            )
        }

        when(result){
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(Result.Success(result.data.data?.map {
                    it.toOrder()
                }?: emptyList()))
            }
        }
    }
}