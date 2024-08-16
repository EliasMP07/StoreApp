package com.devdroid07.storeapp.store.domain.usecases.account

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.AccountRepository

class UpdateProfileUseCase(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(
        name: String,
        lastName: String,
        image: String,
    ): EmptyResult<DataError.Network> {
        return repository.updateProfile(
            name = name,
            lastname = lastName,
            image = image
        )
    }
}