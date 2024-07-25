package com.devdroid07.storeapp.store.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("id") val idProduct: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("category") val category: String,
    @SerializedName("quantity") val quantity: Int
)
