package com.devdroid07.storeapp.auth.data.remote.response

import com.google.gson.annotations.SerializedName


data class StoreResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val data: T?
)