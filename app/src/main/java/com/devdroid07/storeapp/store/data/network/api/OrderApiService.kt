package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.order.OrderDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderApiService {

    @GET("orders/getAllOrders/{client_id}")
    suspend fun getAllOrders(
        @Path("client_id") userId: String
    ): Response<StoreResponse<List<OrderDto>>>

    @GET("orders/getSingleOrder/{order_id}")
    suspend fun getSingleOrder(
        @Path("order_id") orderId: String
    ): Response<StoreResponse<OrderDto>>

}