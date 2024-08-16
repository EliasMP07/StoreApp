package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.card.CardDto
import com.devdroid07.storeapp.store.data.network.dto.store.card.CardRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApiService {
    @GET("card/getAllCards/{user_id}")
    suspend fun getAllMyCards(@Path("user_id") userId: String): Response<StoreResponse<List<CardDto>>>

    @POST("card/create")
    suspend fun createCard(@Body cardRequest: CardRequest): Response<StoreResponse<CardDto>>
}