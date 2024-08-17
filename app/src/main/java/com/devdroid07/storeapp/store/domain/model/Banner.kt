package com.devdroid07.storeapp.store.domain.model

data class Banner(
    val description: String = "",
    val discountPercentage: Double? = null,
    val endDate: String = "",
    val id: Int = 0,
    val imageUrl: String = "",
    val startDate: String = "",
    val type: String = ""
)
