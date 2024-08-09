package com.devdroid07.storeapp.store.domain.usecases.cart

data class CartUseCases(
    val addMyCartUseCase: AddMyCartUseCase,
    val getMyCartUseCase: GetMyCartUseCase,
    val removeProductMyCartUseCase: RemoveProductMyCartUseCase
)
