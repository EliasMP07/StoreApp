package com.devdroid07.storeapp.store.presentation.account

sealed interface AccountAction {
    data object OpenDrawer: AccountAction
}