package com.devdroid07.storeapp.store.domain.usecases

data class StoreUseCases(
    val getAllProducts: GetAllProducts,
    val getSingleProduct: GetSingleProduct,
    val getMyCartUseCase: GetMyCartUseCase,
    val addMyCartUseCase: AddMyCartUseCase
)
