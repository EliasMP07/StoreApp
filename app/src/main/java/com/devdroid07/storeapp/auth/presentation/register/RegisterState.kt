@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.devdroid07.storeapp.auth.domain.validator.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isVisiblePassword: Boolean = false,
    val image: String = "",
    val isRegistering: Boolean = false,
    val imagePreview: String = "",
    val canRegister: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val showOptionProfileImage: Boolean = false,
    val showCamaraRationale: Boolean = false,
    val hasPermissionCamara: Boolean = false
)
