@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.myCart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.SwipeToDeleteContainer
import com.devdroid07.storeapp.store.presentation.myCart.MyCartAction
import com.devdroid07.storeapp.store.presentation.myCart.MyCartState

@Composable
fun MyCartContent(
    modifier: Modifier = Modifier,
    state: MyCartState,
    onAction: (MyCartAction) -> Unit
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.myCart, key = {it.idProduct}) { cart ->
            SwipeToDeleteContainer(
                item = cart,
                onDelete = {
                    onAction(MyCartAction.OnRemoveProduct(it.idProduct.toInt()))
                }
            ) {
                ItemCart(
                    cart = cart,
                    onClickCart = {quantity, productId ->
                        onAction(MyCartAction.OnChangeQuantityAndProductId(quantity, productId))
                        onAction(MyCartAction.OnCartClick)
                    }
                )
            }
        }
    }
}