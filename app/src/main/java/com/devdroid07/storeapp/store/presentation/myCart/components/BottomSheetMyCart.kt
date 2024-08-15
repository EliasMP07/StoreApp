package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.store.presentation.myCart.MyCartAction
import com.devdroid07.storeapp.store.presentation.myCart.MyCartState
import kotlinx.coroutines.launch


@Composable
fun BottomSheetMyCart(
    state: MyCartState,
    onAction: (MyCartAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = state.quantity)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.add_more_products),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = {
                if (state.quantity >= 0) {
                    onAction(MyCartAction.UpClickSelectedQuantity)
                    scope.launch {
                        listState.animateScrollToItem(state.quantity)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = stringResource(R.string.content_description_add_product_cart),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(
            contentAlignment = Alignment.Center,
        ) {
            //Lazy column que contiene las cantidad del producto y sus valores que puede tomar del rango de 1..200
            LazyColumn(
                state = listState,
                reverseLayout = true,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(state.quantityList) { quantity ->
                    //Variable que hara que cambie la opacidad del valor superior, inferior y centro
                    val opacity = when (quantity) {
                        state.quantity -> 1f
                        state.quantity - 1, state.quantity + 1 -> 0.5f
                        else -> 0f
                    }
                    //Item pero con la setencia hace que solo se muestren en pantalla 3 valores el superior, el medio y inferior
                    if (opacity > 0f) {
                        ItemQuantityProduct(
                            quantity = quantity,
                            selected = state.quantity == quantity,
                            opacity = opacity
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = {
                if (state.quantity > 1) {
                    onAction(MyCartAction.DownClickSelectedQuantity)
                    scope.launch {
                        listState.animateScrollToItem(state.quantity)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.content_description_remove_product_quantity_cart),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        StoreActionButton(
            text = stringResource(R.string.btn_text_update_cart),
            isLoading = state.isUpdatingQuantity
        ) {
            onAction(MyCartAction.OnUpdateQuantityClick)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}