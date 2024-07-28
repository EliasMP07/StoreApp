@file:OptIn(ExperimentalMaterial3Api::class,
            ExperimentalFoundationApi::class
)

package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
fun BottomSheetReview(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(state.showBottomSheet) {
        if (state.showBottomSheet) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onAction(ProductDetailAction.OnToggleModalBottomSheet)
        },
        sheetState = sheetState
    ) {
        Box {
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(300.dp)
                    .offset(y = (-100).dp),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
            )
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_calification),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PhotoProfile(
                        size = 60.dp,
                        image = state.user?.image.orEmpty()
                    )
                    Text(
                        text = state.user?.email.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                StarRating(
                    iconSize = 40.dp,
                ) { onAction(ProductDetailAction.OnRantingChange(it)) }
                StoreTextField(
                    state = state.comment,
                    startIcon = null,
                    endIcon = null,
                    hint = stringResource(R.string.text_comment),
                    title = null
                )
                StoreActionButton(
                    enabled = state.canReview,
                    text = stringResource(R.string.text_btn_review),
                    isLoading = state.isEvaluating
                ) { onAction(ProductDetailAction.OnReviewProductClick) }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}