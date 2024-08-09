package com.devdroid07.storeapp.store.presentation.updateAddress

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.devdroid07.storeapp.core.presentation.ui.UiText

@OptIn(ExperimentalFoundationApi::class)
data class UpdateAddressState(
    val addressId: String = "",
    val isLoading: Boolean = false,
    val error:UiText? = null,
    val street: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val isCorrectPostalCode: Boolean = false,
    val isExpandedDropdownMenu: Boolean = false,
    val settlementList: List<String> = emptyList(),
    val isUpdatingAddress: Boolean = false,
    val settlement: TextFieldState = TextFieldState(),
    val numberContact: TextFieldState = TextFieldState(),
    val mayoralty: TextFieldState = TextFieldState(),
    val state: TextFieldState = TextFieldState(),
    val references: TextFieldState = TextFieldState()
)
