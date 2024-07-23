package com.devdroid07.storeapp.auth.data.remote.api

import com.devdroid07.storeapp.auth.data.remote.dto.LoginRequest
import com.devdroid07.storeapp.auth.data.remote.dto.RegisterRequest
import com.devdroid07.storeapp.auth.data.remote.response.StoreResponse
import com.devdroid07.storeapp.core.domain.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): StoreResponse<UserDto>

    @POST("users/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): StoreResponse<UserDto>

}

