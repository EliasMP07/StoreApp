package com.devdroid07.storeapp.store.domain.usecases.address

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetAllMyAddressUseCase (
    private val repository: AddressRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Address>, DataError.Network>> {
        return repository.getAllMyAddress()
    }
}