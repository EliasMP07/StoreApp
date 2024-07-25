@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.myCart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.SwipeToDeleteContainer
import com.devdroid07.storeapp.store.presentation.home.componets.HomeShimmerEffect
import com.devdroid07.storeapp.store.presentation.myCart.components.EmptyMyCartScreen
import com.devdroid07.storeapp.store.presentation.myCart.components.ItemCard

@Composable
fun MyCartScreenRoot(
    state: MyCartState,
    onAction: (MyCartAction) -> Unit
) {
    MyCartScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
private fun MyCartScreen(
    state: MyCartState,
    onAction: (MyCartAction) -> Unit
) {
    Scaffold(
        topBar = {
            StoreToolbar(
                title = "Mi carrito",
                isMenu = false,
                isProfile = false,
                onBack = {
                    onAction(MyCartAction.OnBackClick)
                },
                openDrawer = { /*TODO*/ }
            )
        }
    ) { paddingValue ->
        val result = handleResult(
            isLoading = false,
            error = null,
            isEmpty = state.cart.isEmpty(),
            retry = { /*TODO*/ },
            paddingValues = paddingValue
        )
        if (result) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(6) {
                        SwipeToDeleteContainer(
                            item = it,
                            onDelete = {

                            }
                        ) {
                            ItemCard()
                        }
                    }
                }
                ElevatedCard(
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    ),
                    elevation = CardDefaults.cardElevation(10.dp),
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(
                                    text = "Total(9 items)",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Sub total",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Column {
                                Text(
                                    text = "$1000.00",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Text(
                                    text = "$1000.00",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        StoreActionButton(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "Pagar",
                            isLoading = false
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun handleResult(
    isLoading: Boolean,
    error: String?,
    isEmpty: Boolean,
    retry: () -> Unit,
    paddingValues: PaddingValues
): Boolean {
    return when {
        isLoading -> {
            HomeShimmerEffect(
                paddingValues = paddingValues
            )
            false
        }
        isEmpty -> {
            EmptyMyCartScreen()
            false
        }
        error != null -> {
            ErrorContent(
                error = error,
                onRetry = retry
            )
            false
        }

        else -> true
    }
}

@Composable
fun MyCartScreenPreview() {
    StoreAppTheme {
        MyCartScreen(
            state = MyCartState(),
            onAction = {}
        )
    }
}