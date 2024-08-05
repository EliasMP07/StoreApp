package com.devdroid07.storeapp.store.data.remote.dto.store

import com.google.gson.annotations.SerializedName

data class CardRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("expire_date") val expireDate: String,
    @SerializedName("name_headline") val nameHeadline: String,
    @SerializedName("cvv") val cvv: String
)
