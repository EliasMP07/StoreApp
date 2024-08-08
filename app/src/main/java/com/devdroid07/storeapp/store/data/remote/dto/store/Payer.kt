package com.devdroid07.storeapp.store.data.remote.dto.store

import com.google.gson.annotations.SerializedName

class Payer(
    @SerializedName("email") val email: String
)