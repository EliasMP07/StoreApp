package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult

interface AccountRepository {
    suspend fun updateProfile(name: String, lastname: String, image: String): EmptyResult<DataError.Network>
}