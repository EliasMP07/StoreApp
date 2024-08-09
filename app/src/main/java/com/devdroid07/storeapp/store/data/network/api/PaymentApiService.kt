package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.PaymentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApiService {
    @POST("payments/create")
    suspend fun paymentCreateAndOrder(@Body paymentRequest: PaymentRequest): Response<StoreResponse<String>>
}