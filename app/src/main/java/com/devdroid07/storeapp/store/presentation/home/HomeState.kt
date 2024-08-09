package com.devdroid07.storeapp.store.presentation.home

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.domain.User
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Banner
import com.devdroid07.storeapp.store.domain.model.Product

@Stable
data class HomeState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val user: User? = null,
    val productsList: List<Product> = emptyList(),
    val productRecommended: Product = Product(),
    val bannersList : List<Banner> = emptyList()
)