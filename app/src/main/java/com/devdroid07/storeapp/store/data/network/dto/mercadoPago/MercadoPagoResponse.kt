package com.devdroid07.storeapp.store.data.network.dto.mercadoPago


import com.google.gson.annotations.SerializedName

data class MercadoPagoResponse(
    @SerializedName("card_number_length") val cardNumberLength: Int,
    @SerializedName("cardholder") val cardholder: Cardholder,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("date_due") val dateDue: String,
    @SerializedName("date_last_updated") val dateLastUpdated: String,
    @SerializedName("expiration_month") val expirationMonth: Int,
    @SerializedName("expiration_year") val expirationYear: Int,
    @SerializedName("first_six_digits") val firstSixDigits: String,
    @SerializedName("id") val id: String,
    @SerializedName("last_four_digits") val lastFourDigits: String,
    @SerializedName("live_mode") val liveMode: Boolean,
    @SerializedName("luhn_validation") val luhnValidation: Boolean,
    @SerializedName("public_key") val publicKey: String,
    @SerializedName("require_esc") val requireEsc: Boolean,
    @SerializedName("security_code_length") val securityCodeLength: Int,
    @SerializedName("status") val status: String,
)