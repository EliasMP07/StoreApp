package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.EmptyListScreen
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailAction
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailState

@Composable
fun BottomSheetReviews(
    state: ProductDetailState,
    spacing: Dimensions,
    onAction: (ProductDetailAction) -> Unit,
) {
    Column {
        val result = handleResultView(
            isLoading = state.isLoadingReview,
            contentLoading = {
                ReviewsShimmerEffect(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    paddingValues = PaddingValues(10.dp)
                )
            },
            isEmpty = state.reviewsList.isEmpty(),
            contentEmpty = {
                EmptyListScreen(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.empty_reviews),
                    image = R.drawable.img_review
                )
            },
            error = state.errorReviews,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = { onAction(ProductDetailAction.OnRetryReview) })
            }
        )

        if (result) {
            LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding()
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.background
                    ),
            ) {
                items(
                    state.reviewsList,
                    key = { it.id }) { review ->
                    ItemReviewProduct(
                        review = review,
                        spacing = spacing
                    )
                }
            }
        }
        ProductReviewForm(
            state = state,
            onAction = onAction
        )
    }
}