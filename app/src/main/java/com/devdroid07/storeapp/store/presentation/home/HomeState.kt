package com.devdroid07.storeapp.store.presentation.home

import com.devdroid07.storeapp.store.domain.model.Product

data class HomeState(
    val vale: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = listOf(),
    val productRecomended: Product = Product()
)