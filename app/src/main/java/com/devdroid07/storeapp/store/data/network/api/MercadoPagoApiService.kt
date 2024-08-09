package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.BuildConfig
import com.devdroid07.storeapp.store.data.network.dto.mercadoPago.MercadoPagoCardTokenRequest
import com.devdroid07.storeapp.store.data.network.dto.mercadoPago.MercadoPagoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MercadoPagoApiService {


    @POST("v1/card_tokens?public_key=${BuildConfig.API_KEY_MERCADOPAGO}")
    suspend fun createCardToken(
        @Body body: MercadoPagoCardTokenRequest
    ): Response<MercadoPagoResponse>

}