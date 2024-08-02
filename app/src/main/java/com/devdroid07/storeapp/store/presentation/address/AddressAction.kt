package com.devdroid07.storeapp.store.presentation.address

sealed interface AddressAction {
    data object OnToggleDropdownMenuClick: AddressAction
    data class OnSettlementChange(val settlement: String): AddressAction
}