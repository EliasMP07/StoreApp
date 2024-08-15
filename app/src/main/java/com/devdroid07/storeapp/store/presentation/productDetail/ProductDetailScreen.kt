@file:OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class,
    ExperimentalFoundationApi::class
)

package com.devdroid07.storeapp.store.presentation.productDetail

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonBack
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreIconButtonFavorite
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreSnackBar
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateEnterBottom
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.store.presentation.productDetail.components.BottomSheetReviews
import com.devdroid07.storeapp.store.presentation.productDetail.components.FooterProductDetail
import com.devdroid07.storeapp.store.presentation.productDetail.components.ProductDetailShimmerEffect
import kotlinx.coroutines.launch

@Composable
fun ProductDetailRootScreenRoot(
    context: Context,
    viewModel: ProductDetailViewModel,
    navigateToCart: () -> Unit,
    onBack: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )
    )

    BackHandler {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        } else {
            onBack()
        }
    }

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ProductDetailEvent.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.error.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }
            }
            is ProductDetailEvent.Success -> {
                scope.launch {
                    if (scaffoldState.bottomSheetState.isVisible) {
                        scaffoldState.bottomSheetState.hide()
                    }
                    val result = snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short,
                        actionLabel = event.snackBarStyle.type
                    )
                    when(result){
                        SnackbarResult.ActionPerformed -> navigateToCart()
                        else -> Unit
                    }
                }
            }
        }
    }


    ProductDetailScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = { action ->
            when (action) {
                ProductDetailAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        scaffoldState = scaffoldState
    )

}

@Composable
private fun ProductDetailScreen(
    state: ProductDetailState,
    scaffoldState: BottomSheetScaffoldState,
    snackbarHostState: SnackbarHostState,
    onAction: (ProductDetailAction) -> Unit,
) {

    val scope = rememberCoroutineScope()

    val spacing = LocalSpacing.current


    BottomSheetScaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){
                StoreSnackBar(snackbarData = it, labelButton = state.labelButton)
            }
        },
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = 30.dp,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetReviews(
                state = state,
                spacing = spacing,
                onAction = onAction
            )
        }) { paddingValue ->
        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                ProductDetailShimmerEffect(
                    paddingValues = paddingValue
                )
            },
            error = state.error,
            errorContent = {
                ErrorContent(
                    error = it,
                    onRetry = { onAction(ProductDetailAction.OnRetry) }
                )
            }
        )
        if (result) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.Center),
                        model = state.product.image,
                        contentDescription = stringResource(id = R.string.content_description_img_product)
                    )

                    this@Column.AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        visible = !state.product.isFavorite,
                        exit = scaleOut(),
                        enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
                    ) {
                        StoreIconButtonFavorite(
                            isFavorite = false,
                            onClick = {
                                onAction(ProductDetailAction.AddFavoriteClick(state.product.id.toString()))
                            }
                        )
                    }

                    this@Column.AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        visible = state.product.isFavorite,
                        exit = scaleOut(),
                        enter = scaleIn(animationSpec = spring(Spring.DampingRatioHighBouncy))
                    ) {
                        StoreIconButtonFavorite(
                            isFavorite = true,
                            onClick = {
                                onAction(ProductDetailAction.RemoveFavoriteClick(state.product.id.toString()))
                            }
                        )
                    }
                    StoreIconButtonBack(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp),
                        onClick = {
                            onAction(ProductDetailAction.OnBackClick)
                        }
                    )

                }
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateEnterBottom(),
                    shape = RoundedCornerShape(
                        topEnd = 25.dp,
                        topStart = 25.dp
                    ),
                    elevation = CardDefaults.cardElevation(10.dp),
                ) {
                    FooterProductDetail(
                        state = state,
                        onAction = onAction,
                        onClickReview = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }

            }

        }
    }

}


@Preview
@Composable
private fun ProductDetailRootScreenPreview() {
    StoreAppTheme {
        ProductDetailScreen(
            state = ProductDetailState(),
            onAction = {},
            snackbarHostState = SnackbarHostState(),
            scaffoldState = rememberBottomSheetScaffoldState()
        )
    }
}