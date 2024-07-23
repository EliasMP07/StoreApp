package com.devdroid07.storeapp.auth.presentation.register

import com.devdroid07.storeapp.core.presentation.ui.UiText

sealed interface RegisterEvent {

    data object OnRegisterSuccess: RegisterEvent

    data class Error(val message: UiText): RegisterEvent

}