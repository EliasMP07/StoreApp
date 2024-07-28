package com.devdroid07.storeapp.store.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewRequest(
    @SerializedName("product_id") val productId: String,
    @SerializedName("user_id")val userId: String,
    @SerializedName("comment")val comment: String?,
    @SerializedName("rating") val rating: Double
)
