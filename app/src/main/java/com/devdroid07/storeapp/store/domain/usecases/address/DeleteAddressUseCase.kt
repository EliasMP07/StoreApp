package com.devdroid07.storeapp.store.domain.usecases.address

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.AddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAddressUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: Int): EmptyResult<DataError.Network>{
        return withContext(Dispatchers.IO){
            repository.deleteAddress(idAddress = addressId)
        }
    }
}