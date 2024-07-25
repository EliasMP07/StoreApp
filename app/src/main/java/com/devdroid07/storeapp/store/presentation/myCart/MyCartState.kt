package com.devdroid07.storeapp.store.presentation.myCart

import com.devdroid07.storeapp.store.domain.model.Cart

data class MyCartState(
    val myCart: List<Cart> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
