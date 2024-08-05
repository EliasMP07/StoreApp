package com.devdroid07.storeapp.store.presentation.address

sealed interface AddressAction {
    data object OnToggleDropdownMenuClick: AddressAction
    data class OnSettlementChange(val settlement: String): AddressAction
    data object OnCreateAddress: AddressAction
    data class OnDeleteAddress(val addressId: Int): AddressAction
    data object OnRetryClick: AddressAction
    data object OnBackClick: AddressAction
    data class OnPaymentClick (val addressId: Int): AddressAction
}