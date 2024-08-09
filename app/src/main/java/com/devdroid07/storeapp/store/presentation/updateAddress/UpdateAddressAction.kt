package com.devdroid07.storeapp.store.presentation.updateAddress

sealed interface UpdateAddressAction {
    data object OnUpdateAddress : UpdateAddressAction
    data object OnToggleDropdownMenuClick: UpdateAddressAction
    data class OnSettlementChange(val settlement: String): UpdateAddressAction
    data object OnBackClick: UpdateAddressAction
}