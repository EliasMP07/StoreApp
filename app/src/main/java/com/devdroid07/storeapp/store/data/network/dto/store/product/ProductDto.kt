package com.devdroid07.storeapp.store.data.network.dto.store.product

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: String,
    @SerializedName("description") val description: String,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("category") val category: String,
    @SerializedName("image") val image: String,
    @SerializedName("rating") val  rating: RatingDto
)
