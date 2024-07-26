package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import com.devdroid07.storeapp.core.presentation.ui.UiText

@Composable
fun handleResultView(
    isLoading: Boolean,
    contentLoading:  @Composable () -> Unit,
    isEmpty: Boolean = false,
    contentEmpty: @Composable () -> Unit = {},
    error: UiText?,
    retry: () -> Unit,
): Boolean {
    return when {
        isLoading -> {
            contentLoading()
            false
        }
        error != null -> {
            ErrorContent(error = error, onRetry = retry)
            false
        }
        isEmpty -> {
            contentEmpty()
            false
        }
        else -> true
    }
}