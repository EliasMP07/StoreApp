package com.devdroid07.storeapp.store.presentation.payment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Card

@OptIn(ExperimentalFoundationApi::class)
data class PaymentState(
    val addressId: String = "",
    val cards: List<Card> = emptyList(),
    val isLoading: Boolean = false,
    val isCreatingCard: Boolean = false,
    val error: UiText? = null,
    val cvv: TextFieldState = TextFieldState(),
    val numberCard: TextFieldState = TextFieldState(),
    val nameHeadlineCard: TextFieldState = TextFieldState(),
    val expireDate: TextFieldState = TextFieldState()
)
