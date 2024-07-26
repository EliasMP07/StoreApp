package com.devdroid07.storeapp.store.presentation.home

import com.devdroid07.storeapp.core.domain.User
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Product

data class HomeState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val user: User? = null,
    val products: List<Product> = listOf(),
    val productRecomended: Product = Product()
)