package com.devdroid07.storeapp.store.domain.model

data class Order(
    val id: Int,
    val timestamp: String,
    val status: String,
    val products: List<Product>,
    val address: Address,
)
