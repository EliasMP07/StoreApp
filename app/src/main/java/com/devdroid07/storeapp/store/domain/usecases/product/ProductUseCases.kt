package com.devdroid07.storeapp.store.domain.usecases.product

data class ProductUseCases(
    val getAllProducts: GetAllProducts,
    val getAllBannersUseCases: GetAllBannersUseCases,
    val getSingleProduct: GetSingleProduct,
    val addReviewProductUseCase: AddReviewProductUseCase,
    val getReviewsProductUseCase: GetReviewsProductUseCase,
    val searchProductUseCase: SearchProductUseCase
)
