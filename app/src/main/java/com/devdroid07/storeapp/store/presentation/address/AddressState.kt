package com.devdroid07.storeapp.store.presentation.address

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Address

@Stable
@OptIn(ExperimentalFoundationApi::class)
data class AddressState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val addressList: List<Address> = emptyList(),
    val street: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val isCorrectPostalCode: Boolean = false,
    val canCreateAddress: Boolean = false,
    val isExpandedDropdownMenu: Boolean = false,
    val settlementList: List<String> = emptyList(),
    val isCreatingAddress: Boolean = false,
    val settlement: TextFieldState= TextFieldState(),
    val numberContact: TextFieldState = TextFieldState(),
    val mayoralty: TextFieldState = TextFieldState(),
    val state: TextFieldState = TextFieldState(),
    val references: TextFieldState = TextFieldState()
)
