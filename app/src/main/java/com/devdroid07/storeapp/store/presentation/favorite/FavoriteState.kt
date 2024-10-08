package com.devdroid07.storeapp.store.presentation.favorite

import androidx.compose.runtime.Stable
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Product

@Stable
data class FavoriteState(
    val productFavorites: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)
