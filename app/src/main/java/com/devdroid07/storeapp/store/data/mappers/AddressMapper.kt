package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.remote.dto.store.AddressDto
import com.devdroid07.storeapp.store.domain.model.Address

fun AddressDto.toAddress(): Address{
    return Address(
        id = id,
        street =street,
        state = state,
        settlement =  settlement,
        mayoralty = mayoralty,
        postalCode =  postalCode,
        phoneNumber = phoneNumber
    )
}