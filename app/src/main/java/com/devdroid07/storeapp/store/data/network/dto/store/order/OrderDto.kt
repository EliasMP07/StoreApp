package com.devdroid07.storeapp.store.data.network.dto.store.order

import com.devdroid07.storeapp.store.data.network.dto.store.address.AddressDto
import com.devdroid07.storeapp.store.data.network.dto.store.product.ProductDto
import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("id")val id: Int,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("status") val status: String,
    @SerializedName("products") val products: List<ProductDto>,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("transaction_amount") val transactionAmount: Double,
)
