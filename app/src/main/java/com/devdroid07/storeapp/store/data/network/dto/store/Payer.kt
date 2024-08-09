package com.devdroid07.storeapp.store.data.network.dto.store

import com.google.gson.annotations.SerializedName

class Payer(
    @SerializedName("email") val email: String
)