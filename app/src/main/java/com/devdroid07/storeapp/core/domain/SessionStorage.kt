package com.devdroid07.storeapp.core.domain

interface SessionStorage {
    suspend fun set(user: User?)
    suspend fun get(): User?
}