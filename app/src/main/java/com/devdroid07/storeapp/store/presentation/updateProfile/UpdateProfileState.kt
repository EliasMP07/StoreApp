package com.devdroid07.storeapp.store.presentation.updateProfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

@OptIn(ExperimentalFoundationApi::class)
data class UpdateProfileState(
    val name: TextFieldState = TextFieldState(),
    val lastname: TextFieldState = TextFieldState(),
    val image: String = "",
    val isUpdatingProfile: Boolean = false,
    val imagePreview: String = "",
    val showOptionProfileImage: Boolean = false,
    val showCamaraRationale: Boolean = false
)
