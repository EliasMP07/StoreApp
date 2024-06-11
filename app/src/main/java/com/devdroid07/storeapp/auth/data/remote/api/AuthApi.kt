package com.devdroid07.storeapp.auth.data.remote.api

import com.devdroid07.storeapp.auth.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}

data class LoginRequest(
    val email: String,
    val password: String
)