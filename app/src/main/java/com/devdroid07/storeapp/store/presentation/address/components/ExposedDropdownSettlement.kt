@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.address.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.store.presentation.address.AddressAction
import com.devdroid07.storeapp.store.presentation.address.AddressState

@Composable
fun ExposedDropdownMenuSettlement(
    modifier: Modifier = Modifier,
    state: AddressState,
    onAction: (AddressAction) -> Unit,
) {

    ExposedDropdownMenuBox(
        expanded = state.isExpandedDropdownMenu,
        onExpandedChange = {
            if (state.isCorrectPostalCode) {
                onAction(AddressAction.OnToggleDropdownMenuClick)
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
            title = "Localidad"
        )

        ExposedDropdownMenu(
            expanded = state.isExpandedDropdownMenu,
            onDismissRequest = {
                onAction(AddressAction.OnToggleDropdownMenuClick)
            }) {
            state.settlementList.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onAction(AddressAction.OnSettlementChange(option))
                        onAction(AddressAction.OnToggleDropdownMenuClick)
                    }
                )
            }
        }
    }
}
