@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.address

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.SwipeToDeleteContainer
import com.devdroid07.storeapp.store.presentation.address.components.BottomSheetAddAddress
import com.devdroid07.storeapp.store.presentation.address.components.ItemAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddressScreenRoot(
    state: AddressState,
    onAction: (AddressAction) -> Unit,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

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


    AddressScreen(
        state = state,
        scope = scope,
        scaffoldState = scaffoldState,
        onAction = onAction,
    )
}

@Composable
fun AddressScreen(
    state: AddressState,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    onAction: (AddressAction) -> Unit,
) {

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        topBar = {
            StoreToolbar(
                title = "Direccion de entrega",
                openDrawer = {},
                onBack = {

                },
                isProfile = false,
                isMenu = false,
            )
        },
        sheetContent = {
            BottomSheetAddAddress(
                state = state,
                onAction = onAction
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(2) {
                    SwipeToDeleteContainer(
                        item = it,
                        onDelete = {

                        }
                    ) {
                        ItemAddress()
                    }
                }
                item {
                    StoreActionButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Agregar direccion",
                        isLoading = false
                    ) {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                }
            }
        }
    }
}