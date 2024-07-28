@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.productDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.devdroid07.storeapp.core.domain.User
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val quantity: Int = 1,
    val user: User? = null,
    val comment: TextFieldState = TextFieldState(),
    val canReview: Boolean = false,
    val isEvaluating: Boolean = false,
    val isExpanded: Boolean = false,
    val showBottomSheet: Boolean = false,
    val product: Product = Product()
)
