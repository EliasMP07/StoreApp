package com.devdroid07.storeapp.store.data.remote.dto.mercadoPago


import com.google.gson.annotations.SerializedName

data class Cardholder(
    @SerializedName("identification")
    val identification: Identification,
    @SerializedName("name")
    val name: String
)