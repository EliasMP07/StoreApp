package com.devdroid07.storeapp.core.data.network

import com.google.gson.annotations.SerializedName


data class StoreResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val data: T?
)