package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.core.domain.UserDto
import com.devdroid07.storeapp.store.data.network.dto.store.account.UpdateProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface AccountApiService {

    @PUT("users/update")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<StoreResponse<UserDto>>
}