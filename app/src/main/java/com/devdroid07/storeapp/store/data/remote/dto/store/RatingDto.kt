package com.devdroid07.storeapp.store.data.remote.dto.store

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val count: Int
)
