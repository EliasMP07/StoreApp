package com.devdroid07.storeapp.store.domain.model

data class Order(
    val id: Int = 0,
    val timestamp: String = "",
    val status: String = "",
    val products: List<Product> = emptyList(),
    val address: Address = Address(),
    val transactionAmount: Double = 0.0,
)
