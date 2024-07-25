package com.devdroid07.storeapp.store.domain.model

data class Cart(
    val idProduct: String,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val rating: String,
    val category: String,
    val quantity: String
)
