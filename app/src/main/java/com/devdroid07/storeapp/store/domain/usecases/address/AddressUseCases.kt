package com.devdroid07.storeapp.store.domain.usecases.address

data class AddressUseCases(
    val createAddressUseCase: CreateAddressUseCase,
    val getAllMyAddressUseCase: GetAllMyAddressUseCase,
    val getInfoByPostalCodeUseCase: GetInfoByPostalCodeUseCase,
    val deleteAddressUseCase: DeleteAddressUseCase
)
