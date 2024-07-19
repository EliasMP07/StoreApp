package com.devdroid07.storeapp.store.presentation.productDetail

sealed interface ProductDetailAction {
    data object OnBackClick: ProductDetailAction
    data object OnMoreInfoClick: ProductDetailAction
}