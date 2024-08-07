package com.devdroid07.storeapp.store.data.remote.dto.mercadoPago

import com.devdroid07.storeapp.store.data.Cardholder
import com.google.gson.annotations.SerializedName

class MercadoPagoCardTokenBody(
    @SerializedName("security_code") val securityCode: String,
    @SerializedName("expiration_year") val expirationYear: String,
    @SerializedName("expiration_month") val expirationMonth: Int,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("cardholder") val cardHolder: Cardholder
)