package com.devdroid07.storeapp.store.data.network.dto.store.product

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id") val id: Int,
    @SerializedName("image_author") val imageAuthor: String,
    @SerializedName("author") val author: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("comment") val comment: String,
    @SerializedName("created") val created: String
)
