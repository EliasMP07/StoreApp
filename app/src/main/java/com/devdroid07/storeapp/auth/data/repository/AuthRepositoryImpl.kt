package com.devdroid07.storeapp.auth.data.repository

import com.devdroid07.storeapp.auth.data.remote.api.AuthApi
import com.devdroid07.storeapp.auth.data.remote.dto.LoginRequest
import com.devdroid07.storeapp.auth.data.remote.dto.RegisterRequest
import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.core.data.mappers.toUser
import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.User
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = safeCall2 {
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
        password: String
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            api.register(
                RegisterRequest(
                    image = image,
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

}
