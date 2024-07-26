package com.devdroid07.storeapp.auth.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, image: String, password: String): EmptyResult<DataError.Network>
}