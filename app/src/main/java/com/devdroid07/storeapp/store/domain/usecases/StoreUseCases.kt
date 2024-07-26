package com.devdroid07.storeapp.store.domain.usecases

data class StoreUseCases(
    val getAllProducts: GetAllProducts,
    val getSingleProduct: GetSingleProduct,
    val getMyCartUseCase: GetMyCartUseCase,
    val addMyCartUseCase: AddMyCartUseCase,
    val removeProductMyCartUseCase: RemoveProductMyCartUseCase,
    val removeFavoriteProductUseCase: RemoveProductMyFavoritesUseCase,
    val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    val getFavoritesProductsUseCase: GetFavoritesProductsUseCase
)
