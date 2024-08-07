package com.devdroid07.storeapp.store.presentation.pay

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.model.Card
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay

@Stable
data class FinishPayState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val totalPrice: Double = 0.0,
    val card: Card = Card(),
    val address: Address = Address(),
    val pay: ProgressPay = ProgressPay.LOADING,
    val isPaying: Boolean = false
)
