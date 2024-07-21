@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.devdroid07.storeapp.auth.domain.usecases.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isVisiblePassword: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
)
