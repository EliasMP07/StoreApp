package com.devdroid07.storeapp.store.domain.model

import com.google.gson.annotations.SerializedName

data class PostalCode(
    val codigoPostal: String = "",
    val tipoAsentamiento: String = "",
    val asentamiento: String = "",
    val municipio: String = "",
    val estado: String = "",
    val ciudad: String = "",
    val pais: String = ""
)
