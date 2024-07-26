package com.devdroid07.storeapp.store.presentation.myCart

import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Cart

data class MyCartState(
    val myCart: List<Cart> = emptyList(),
    val error: UiText? = null,
    val isLoading: Boolean = false
)
