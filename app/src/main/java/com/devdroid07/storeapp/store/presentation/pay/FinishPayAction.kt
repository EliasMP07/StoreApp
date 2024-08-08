package com.devdroid07.storeapp.store.presentation.pay

sealed interface FinishPayAction {
    data object OnBackClick: FinishPayAction
    data object OnPayClick: FinishPayAction
    data object OnNavigateHomeClick: FinishPayAction
    data object GoToChangePaymentMethodClick: FinishPayAction
}