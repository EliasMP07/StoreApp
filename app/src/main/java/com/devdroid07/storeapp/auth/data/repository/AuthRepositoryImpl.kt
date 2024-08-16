package com.devdroid07.storeapp.auth.data.repository

import com.devdroid07.storeapp.auth.data.network.api.AuthApiService
import com.devdroid07.storeapp.auth.data.network.dto.LoginRequest
import com.devdroid07.storeapp.auth.data.network.dto.RegisterRequest
import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.core.data.mappers.toUser
import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            api.login(
                LoginRequest(
                    email = email,
                    password = password
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

    override suspend fun register(
        email: String,
        image: String,
        name: String,
        lastname: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            api.register(
                RegisterRequest(
                    image = image,
                    email = email,
                    password = password,
                    name = name,
                    lastname = lastname
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
