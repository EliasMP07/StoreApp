package com.devdroid07.storeapp.store.data.network.dto.store

import com.google.gson.annotations.SerializedName

data class CardDto(
    @SerializedName("id") val id: Int,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("expire_date") val expireDate: String,
    @SerializedName("name_headline") val nameHeadline: String,
    @SerializedName("cvv") val cvv: String
)
