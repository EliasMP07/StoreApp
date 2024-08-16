package com.devdroid07.storeapp.store.presentation.account

sealed interface AccountAction {
    data object OnLogoutClick: AccountAction
    data object OpenDrawer: AccountAction
    data object OnUpdateProfile: AccountAction
}