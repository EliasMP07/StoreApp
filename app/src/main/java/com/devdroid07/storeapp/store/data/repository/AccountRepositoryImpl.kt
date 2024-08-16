package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.mappers.toUser
import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.network.api.AccountApiService
import com.devdroid07.storeapp.store.data.network.dto.store.account.UpdateProfileRequest
import com.devdroid07.storeapp.store.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val accountApiService: AccountApiService
): AccountRepository {
    override suspend fun updateProfile(
        name: String,
        lastname: String,
        image: String,
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            accountApiService.updateProfile(
                UpdateProfileRequest(
                    userId = sessionStorage.get()?.id.orEmpty(),
                    name = name,
                    lastname = lastname,
                    imageProfile = image,
                    email =  sessionStorage.get()?.email.orEmpty(),
                )
            )
        }

        if (result is Result.Success){
            sessionStorage.set(
                user = result.data.data?.toUser()
            )
        }

        return result.asEmptyDataResult()
    }
}