package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.repository.StoreRepository

class GetAllMyAddressUseCase (
    private val repository: StoreRepository
) {
    suspend operator fun invoke(): Result<List<Address>, DataError.Network>{
        return repository.getAllMyAddress()
    }
}