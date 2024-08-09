package com.devdroid07.storeapp.auth.data.network.api

import com.devdroid07.storeapp.auth.data.network.dto.LoginRequest
import com.devdroid07.storeapp.auth.data.network.dto.RegisterRequest
import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.core.domain.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<StoreResponse<UserDto>>

    @POST("users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<StoreResponse<UserDto>>
}

