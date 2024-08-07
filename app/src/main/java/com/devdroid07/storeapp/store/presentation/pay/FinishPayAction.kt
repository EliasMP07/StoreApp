package com.devdroid07.storeapp.store.presentation.pay

import com.devdroid07.storeapp.store.presentation.address.AddressAction

sealed interface FinishPayAction {
    data object OnRetryClick: FinishPayAction
    data object OnBackClick: FinishPayAction
    data object OnPayClick: FinishPayAction
    data object OnChangeOtherCardClick: FinishPayAction
}