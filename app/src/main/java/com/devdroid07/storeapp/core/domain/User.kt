package com.devdroid07.storeapp.core.domain

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val email: String,
    val name: String,
    val lastname: String,
    val image: String,
    val token: String
)


data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("image") val image: String,
    @SerializedName("session_token") val token: String
)