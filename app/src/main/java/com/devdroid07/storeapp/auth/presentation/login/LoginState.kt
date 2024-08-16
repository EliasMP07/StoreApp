package com.devdroid07.storeapp.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable

@Stable
@OptIn(ExperimentalFoundationApi::class)
data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isVisiblePassword: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val passwordCorrect: Boolean = false,
)
