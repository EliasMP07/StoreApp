package com.devdroid07.storeapp.store.presentation.myCart

sealed interface MyCartAction {
    data object OnBackClick: MyCartAction
    data object OnCartClick: MyCartAction
    data object OnContinuePayClick: MyCartAction
    data object UpClickSelectedQuantity: MyCartAction
    data object DownClickSelectedQuantity: MyCartAction
    data class OnRemoveProduct(val idProduct: Int): MyCartAction
    data class OnChangeQuantityAndProductId(val quantity: Int, val productId: String): MyCartAction
    data object OnRetryClick: MyCartAction
    data object OnConfirmRemove: MyCartAction
    data object OnRestoreRemove:  MyCartAction
    data object OnUpdateQuantityClick: MyCartAction
}