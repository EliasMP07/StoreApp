package com.devdroid07.storeapp.store.domain.usecases.address

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.repository.AddressRepository
import com.devdroid07.storeapp.store.domain.repository.StoreRepository

class CreateAddressUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(
        street: String,
        postalCode: String,
        state: String,
        mayoralty: String,
        settlement: String,
        phoneNumber: String,
        reference: String,
    ): Result<Address, DataError.Network> {
        return repository.createAddress(
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