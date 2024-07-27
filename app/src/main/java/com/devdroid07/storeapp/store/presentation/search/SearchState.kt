package com.devdroid07.storeapp.store.presentation.search

import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Product

data class SearchState(
    val products: List<Product> = emptyList(),
    val query: String = "",
    val isEmpty: Boolean = false,
    val error: UiText? = null,
    val isSearching: Boolean = false
)
