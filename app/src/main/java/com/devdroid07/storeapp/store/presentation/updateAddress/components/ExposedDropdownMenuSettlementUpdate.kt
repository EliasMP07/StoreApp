@file:OptIn(ExperimentalMaterial3Api::class,
            ExperimentalFoundationApi::class
)

package com.devdroid07.storeapp.store.presentation.updateAddress.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressAction
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressState

@Composable
fun ExposedDropdownMenuSettlementUpdate(
    modifier: Modifier = Modifier,
    state: UpdateAddressState,
    onAction: (UpdateAddressAction) -> Unit,
) {

    ExposedDropdownMenuBox(
        expanded = state.isExpandedDropdownMenu,
        onExpandedChange = {
            if (state.isCorrectPostalCode) {
                onAction(UpdateAddressAction.OnToggleDropdownMenuClick)
            }
        },
        modifier = modifier
    ) {

        StoreTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            enable = state.isCorrectPostalCode,
            readOnly = true,
            state = state.settlement,
            startIcon = null,
            endIcon = if(state.isExpandedDropdownMenu)Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown ,
            hint = "",
            title = stringResource(R.string.title_settlement)
        )

        ExposedDropdownMenu(
            expanded = state.isExpandedDropdownMenu,
            onDismissRequest = {
                onAction(UpdateAddressAction.OnToggleDropdownMenuClick)
            }) {
            state.settlementList.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onAction(UpdateAddressAction.OnSettlementChange(option))
                        onAction(UpdateAddressAction.OnToggleDropdownMenuClick)
                    }
                )
            }
        }
    }
}
