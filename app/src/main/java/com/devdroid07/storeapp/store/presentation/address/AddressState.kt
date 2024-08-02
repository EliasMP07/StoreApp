package com.devdroid07.storeapp.store.presentation.address

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable

@Stable
@OptIn(ExperimentalFoundationApi::class)
data class AddressState(
    val street: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val isCorrectPostalCode: Boolean = false,
    val isExpandedDropdownMenu: Boolean = false,
    val settlementList: List<String> = emptyList(),
    val settlement: TextFieldState= TextFieldState(),
    val numberContact: TextFieldState = TextFieldState(),
    val mayoralty: TextFieldState = TextFieldState(),
    val state: TextFieldState = TextFieldState(),
    val references: TextFieldState = TextFieldState()
)
