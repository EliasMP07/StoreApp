package com.devdroid07.storeapp.store.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("product_id") val idProduct: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("category") val category: String,
    @SerializedName("quantity") val quantity: String
)
