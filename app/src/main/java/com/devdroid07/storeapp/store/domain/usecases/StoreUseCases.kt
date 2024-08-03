package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.store.domain.usecases.address.CreateAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetAllMyAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetInfoByPostalCodeUseCase

data class StoreUseCases(
    val getAllProducts: GetAllProducts,
    val getSingleProduct: GetSingleProduct,
    val addReviewProductUseCase: AddReviewProductUseCase,
    val getReviewsProductUseCase: GetReviewsProductUseCase,
    val searchProductUseCase: SearchProductUseCase,
    val getMyCartUseCase: GetMyCartUseCase,
    val addMyCartUseCase: AddMyCartUseCase,
    val removeProductMyCartUseCase: RemoveProductMyCartUseCase,
    val removeFavoriteProductUseCase: RemoveProductMyFavoritesUseCase,
    val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    val getFavoritesProductsUseCase: GetFavoritesProductsUseCase
)
