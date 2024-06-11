package com.devdroid07.storeapp.auth.domain.repository

interface AuthRepository {
    suspend fun login(userName: String, password: String): Result<Unit>
    suspend fun register(email: String, userName: String, password: String)
}