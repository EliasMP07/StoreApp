package com.devdroid07.storeapp.store.domain.usecases.address

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.AddressRepository

class UpdateAddressUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(
        id: String,
        street: String,
        postalCode: String,
        state: String,
        mayoralty: String,
        settlement: String,
        phoneNumber: String,
        reference: String,
    ): EmptyResult<DataError.Network> {
        return repository.updateAddress(
            id = id,
            street = street,
            postalCode = postalCode,
            state = state,
            mayoralty = mayoralty,
            settlement = settlement,
            phoneNumber = phoneNumber,
            reference = reference
        )
    }
}