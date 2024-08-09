package com.devdroid07.storeapp.store.domain.usecases.product

import com.devdroid07.storeapp.store.domain.usecases.AddMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetMyCartUseCase

data class ProductUseCases(
    val getAllProducts: GetAllProducts,
    val getAllBannersUseCases: GetAllBannersUseCases,
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
