package com.devdroid07.storeapp.store.data.network.dto.store.product


import com.google.gson.annotations.SerializedName

data class BannerDto(
    @SerializedName("description") val description: String,
    @SerializedName("discount_percentage") val discountPercentage: Double?,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("type") val type: String
)