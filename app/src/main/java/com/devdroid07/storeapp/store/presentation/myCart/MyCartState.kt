package com.devdroid07.storeapp.store.presentation.myCart

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Cart

@Stable
data class MyCartState(
    val myCart: List<Cart> = emptyList(),
    val error: UiText? = null,
    val isLoading: Boolean = false
)
