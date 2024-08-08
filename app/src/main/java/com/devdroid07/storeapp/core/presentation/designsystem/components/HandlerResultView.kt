package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import com.devdroid07.storeapp.core.presentation.ui.UiText

@Composable
fun handleResultView(
    isLoading: Boolean,
    contentLoading:  @Composable () -> Unit,
    isEmpty: Boolean = false,
    errorContent:  @Composable (UiText) -> Unit,
    contentEmpty: @Composable () -> Unit = {},
    error: UiText?
): Boolean {
    return when {
        isLoading -> {
            contentLoading()
            false
        }
        error != null -> {
            errorContent(error)
            false
        }
        isEmpty -> {
            contentEmpty()
            false
        }
        else -> true
    }
}