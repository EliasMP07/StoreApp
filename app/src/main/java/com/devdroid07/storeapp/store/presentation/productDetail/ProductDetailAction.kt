package com.devdroid07.storeapp.store.presentation.productDetail


sealed interface ProductDetailAction {
    data object OnBackClick: ProductDetailAction
    data object OnRetry: ProductDetailAction
    data object OnMoreInfoClick: ProductDetailAction
    data object OnToggleModalBottomSheet: ProductDetailAction
    data class OnAddMyCart(val idProduct: String, val quantity: Int): ProductDetailAction
    data object OnAddProductClick: ProductDetailAction
    data object OnRemoveProductClick: ProductDetailAction
    data class AddFavoriteClick(val productId: String): ProductDetailAction
    data class RemoveFavoriteClick(val productId: String): ProductDetailAction
}