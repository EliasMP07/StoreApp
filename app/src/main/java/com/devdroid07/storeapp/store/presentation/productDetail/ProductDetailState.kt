package com.devdroid07.storeapp.store.presentation.productDetail

import com.devdroid07.storeapp.store.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val quantity: Int = 1,
    val isExpanded: Boolean = false,
    val product: Product = Product()
)
