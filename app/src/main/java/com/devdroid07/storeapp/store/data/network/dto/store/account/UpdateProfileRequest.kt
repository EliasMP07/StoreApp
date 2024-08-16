package com.devdroid07.storeapp.store.data.network.dto.store.account

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("id") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("image") val imageProfile: String,
    @SerializedName("email") val email: String,
)
