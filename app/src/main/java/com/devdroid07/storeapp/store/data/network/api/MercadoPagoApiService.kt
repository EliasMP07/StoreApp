package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.store.data.network.dto.mercadoPago.MercadoPagoCardTokenRequest
import com.devdroid07.storeapp.store.data.network.dto.mercadoPago.MercadoPagoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MercadoPagoApiService {

    @POST("v1/card_tokens?public_key=TEST-9dafbb39-dd54-4733-90eb-c4ee5caf21ac")
    suspend fun createCardToken(
        @Body body: MercadoPagoCardTokenRequest
    ): Response<MercadoPagoResponse>

}