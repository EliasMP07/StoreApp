package com.devdroid07.storeapp.auth.domain.usecases

import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult

class LoginWithEmailAndPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return repository.login(
            email = email,
            password = password
        )
    }
}