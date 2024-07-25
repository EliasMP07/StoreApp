package com.devdroid07.storeapp.store.presentation.myCart

sealed interface MyCartAction {
    data object OnBackClick: MyCartAction
}