package com.devdroid07.storeapp.store.data.network.dto.store

import com.google.gson.annotations.SerializedName

class PaymentRequest(
    @SerializedName("order") val order: OrderRequest,
    @SerializedName("token") val token: String,
    @SerializedName("description") val description: String,
    @SerializedName("payer") val payer: Payer,
    @SerializedName("transaction_amount") val transactionAmount: Double,
    @SerializedName("installments") val installments: Int = 1
)

data class OrderRequest(
    @SerializedName("id_client") val clientId: String,
    @SerializedName("id_address") val addressId: String
)