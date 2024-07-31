@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.components.PhotoProfile
import com.devdroid07.storeapp.core.presentation.designsystem.components.StarRating
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailAction
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailState

@Composable
fun ProductReviewForm(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
) {
    Column(
        Modifier
            .background(
                MaterialTheme.colorScheme.surface.copy(
                    alpha = 0.4f
                )
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.text_calification),
                style = MaterialTheme.typography.titleMedium
            )
            StarRating(
                iconSize = 40.dp,
            ) { onAction(ProductDetailAction.OnRantingChange(it)) }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PhotoProfile(
                size = 50.dp,
                image = state.user?.image.orEmpty()
            )
            StoreTextField(
                state = state.comment,
                startIcon = null,
                endIcon = null,
                hint = stringResource(
                    R.string.text_comment,
                    state.user?.email.orEmpty()
                ),
                title = null
            )
        }
        StoreActionButton(
            enabled = state.canReview,
            text = stringResource(R.string.text_btn_review),
            isLoading = state.isEvaluating
        ) { onAction(ProductDetailAction.OnReviewProductClick) }
    }
}