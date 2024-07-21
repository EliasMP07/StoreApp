package com.devdroid07.storeapp.auth.presentation.register

import com.devdroid07.storeapp.auth.presentation.login.LoginAction

sealed interface RegisterAction {
    data object OnToggleVisibilityPassword : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
}