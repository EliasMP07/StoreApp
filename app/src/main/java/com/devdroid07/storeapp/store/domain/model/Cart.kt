package com.devdroid07.storeapp.store.domain.model

data class Cart(
    val idProduct: String,
    val title: String,
    val price: String,
    val image: String,
    val category: String,
    val quantity: Int
)
