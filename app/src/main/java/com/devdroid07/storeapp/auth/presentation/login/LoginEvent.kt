package com.devdroid07.storeapp.auth.presentation.login

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface LoginEvent {

    data object LoginSuccess: LoginEvent

    data class Error(val message: UiText): LoginEvent

}