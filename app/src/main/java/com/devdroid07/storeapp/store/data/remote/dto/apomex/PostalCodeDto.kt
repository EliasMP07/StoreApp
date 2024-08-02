package com.devdroid07.storeapp.store.data.remote.dto.apomex

import com.google.gson.annotations.SerializedName

data class PostalCodeDto(
     @SerializedName("cp") val codigoPostal: String,
     @SerializedName("asentamiento") val asentamiento: String,
     @SerializedName("tipo_asentamiento") val tipoAsentamiento: String,
     @SerializedName("municipio") val municipio: String,
     @SerializedName("estado") val estado: String,
     @SerializedName("ciudad") val ciudad: String,
     @SerializedName("pais") val pais: String
)
