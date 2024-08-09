package com.devdroid07.storeapp.auth.data.network.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("image") val image: String?,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)