package com.devdroid07.storeapp.auth.domain.usecases

import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult

class SignUpWithEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        lastname: String,
        image: String
    ): EmptyResult<DataError.Network> {
        return repository.register(
            email = email,
            password = password,
            name = name,
            lastname = lastname,
            image = image
        )
    }
}