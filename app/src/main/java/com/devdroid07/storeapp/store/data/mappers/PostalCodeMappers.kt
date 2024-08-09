package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.apomex.PostalCodeDto
import com.devdroid07.storeapp.store.domain.model.PostalCode

fun PostalCodeDto.toPostalCode(): PostalCode{
    return PostalCode(
        codigoPostal = codigoPostal,
        asentamiento = asentamiento,
        tipoAsentamiento = tipoAsentamiento,
        municipio = municipio,
        estado = estado,
        ciudad = ciudad,
        pais = pais
    )
}