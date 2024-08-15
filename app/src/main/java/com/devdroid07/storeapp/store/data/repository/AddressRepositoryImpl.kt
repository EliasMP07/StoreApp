package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.mappers.toAddress
import com.devdroid07.storeapp.store.data.mappers.toPostalCode
import com.devdroid07.storeapp.store.data.network.api.AddressApiService
import com.devdroid07.storeapp.store.data.network.api.CopomexApi
import com.devdroid07.storeapp.store.data.network.dto.store.AddressRequest
import com.devdroid07.storeapp.store.data.network.dto.store.AddressUpdateRequest
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.model.PostalCode
import com.devdroid07.storeapp.store.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddressRepositoryImpl (
    private val addressApiService: AddressApiService,
    private val copomexApi: CopomexApi,
    private val sessionStorage: SessionStorage
): AddressRepository {

    override fun getInfoByPostalCode(postalCode: String): Flow<Result<List<PostalCode>, DataError.Network>> = flow {
        val result = safeCall {
            copomexApi.getInfoByPostalCode(
                codigoPostal = postalCode,
            )
        }
        when (result) {
            is Result.Error -> {
                emit(
                    Result.Error(
                        result.error
                    )
                )
            }
            is Result.Success -> {
                emit(Result.Success(
                    result.data.map {
                        it.postalCodeDto?.toPostalCode() ?: PostalCode()
                    }
                ))
            }
        }
    }

    override fun getAllMyAddress(): Flow<Result<List<Address>, DataError.Network>> = flow {
        val result = safeCall {
            addressApiService.getAllMyAddress(
                userId = sessionStorage.get()?.id.orEmpty()
            )
        }
         when (result) {
            is Result.Error -> {
                emit( Result.Error(result.error))

            }
            is Result.Success -> {
                emit(  Result.Success(result.data.data?.map {
                    it.toAddress()
                }.orEmpty()))
            }
        }
    }

    override suspend fun createAddress(
        street: String,
        postalCode: String,
        state: String,
        mayoralty: String,
        settlement: String,
        phoneNumber: String,
        reference: String,
    ): Result<Address, DataError.Network> {
        val result = safeCall {
            addressApiService.createAddress(
                addressRequest = AddressRequest(
                    userId = sessionStorage.get()?.id.orEmpty(),
                    street = street,
                    postalCode = postalCode,
                    state = state,
                    mayoralty = mayoralty,
                    settlement = settlement,
                    phoneNumber = phoneNumber,
                    reference = reference
                )
            )
        }
        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(
                    result.data.data?.toAddress() ?: Address()
                )
            }
        }
    }

    override suspend fun deleteAddress(idAddress: Int): EmptyResult<DataError.Network> {
        val result = safeCall {
            addressApiService.deleteAddress(idAddress.toString())
        }
        return result.asEmptyDataResult()
    }

    override suspend fun updateAddress(
        id: String,
        street: String,
        postalCode: String,
        state: String,
        mayoralty: String,
        settlement: String,
        phoneNumber: String,
        reference: String,
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            addressApiService.updateAddress(
                AddressUpdateRequest(
                    id = id,
                    street = street,
                    postalCode = postalCode,
                    state = state,
                    mayoralty = mayoralty,
                    settlement = settlement,
                    phoneNumber = phoneNumber,
                    reference = reference
                )
            )
        }

        return result.asEmptyDataResult()
    }

    override suspend fun getAddress(idAddress: String): Result<Address, DataError.Network> {
        val result = safeCall {
            addressApiService.getAddress(addressId = idAddress)
        }

        return when(result){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data?.toAddress()?:Address())
            }
        }
    }
}