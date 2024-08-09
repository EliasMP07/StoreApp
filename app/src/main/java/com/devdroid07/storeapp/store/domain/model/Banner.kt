package com.devdroid07.storeapp.store.domain.model

data class Banner(
    val description: String = "",
    val discountPercentage: String? = null,
    val endDate: String = "",
    val id: String = "",
    val imageUrl: String = "",
    val startDate: String = "",
    val type: String = ""
)
