package com.devdroid07.storeapp.store.domain.model

data class Product(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val image: String = "",
    val ratingCount: Int =  0,
    val ratingRate: Double = 0.0
)
