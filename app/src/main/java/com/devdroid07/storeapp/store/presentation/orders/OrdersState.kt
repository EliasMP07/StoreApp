package com.devdroid07.storeapp.store.presentation.orders

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Order

@Stable
data class OrdersState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val ordersList: List<Order>  = emptyList()
)
