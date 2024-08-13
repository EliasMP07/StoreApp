package com.devdroid07.storeapp.store.presentation.orderDetail

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Order

@Stable
data class OrderDetailState(
    val order: Order = Order(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)
