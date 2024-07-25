package com.devdroid07.storeapp.store.presentation.myCart

sealed interface MyCartAction {
    data object OnBackClick: MyCartAction
    data class OnRemoveProduct(val idProduct: Int): MyCartAction
    data object OnRetryClick: MyCartAction
}