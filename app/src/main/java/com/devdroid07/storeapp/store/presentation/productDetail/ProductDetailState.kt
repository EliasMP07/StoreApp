package com.devdroid07.storeapp.store.presentation.productDetail

import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val quantity: Int = 1,
    val isExpanded: Boolean = false,
    val product: Product = Product()
)
