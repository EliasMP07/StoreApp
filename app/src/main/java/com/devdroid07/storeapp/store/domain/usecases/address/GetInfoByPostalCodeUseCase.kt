package com.devdroid07.storeapp.store.domain.usecases.address

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.PostalCode
import com.devdroid07.storeapp.store.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetInfoByPostalCodeUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(postalCode: String): Flow<Result<List<PostalCode>, DataError.Network>> {
        return repository.getInfoByPostalCode(postalCode = postalCode)
    }
}