package com.devdroid07.storeapp.store.presentation.myCart

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Cart

@Stable
data class MyCartState(
    val myCart: List<Cart> = emptyList(),
    val error: UiText? = null,
    val removedItems: List<Cart> = emptyList(),
    val totalSale: Double = 0.0,
    val quantity: Int = 0,
    val productId: String = "",
    val quantityList: List<Int> = (1..100).toList(),
    val labelButton: UiText? = null,
    val isLoading: Boolean = false,
    val isUpdatingQuantity: Boolean = false
)
