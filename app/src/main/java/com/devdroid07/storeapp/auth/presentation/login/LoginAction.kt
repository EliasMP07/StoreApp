package com.devdroid07.storeapp.auth.presentation.login

sealed interface LoginAction {

    data object OnToggleVisibilityPassword: LoginAction

    data object OnRegisterClick: LoginAction

    data object OnLoginClick: LoginAction

}