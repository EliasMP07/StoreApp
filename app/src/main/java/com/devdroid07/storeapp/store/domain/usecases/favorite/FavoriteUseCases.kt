package com.devdroid07.storeapp.store.domain.usecases.favorite

data class FavoriteUseCases(
    val removeFavoriteProductUseCase: RemoveProductMyFavoritesUseCase,
    val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    val getFavoritesProductsUseCase: GetFavoritesProductsUseCase
)
