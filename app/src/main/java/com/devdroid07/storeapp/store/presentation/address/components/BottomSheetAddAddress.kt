@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.address.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.PhoneIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.store.presentation.address.AddressAction
import com.devdroid07.storeapp.store.presentation.address.AddressState

@Composable
fun BottomSheetAddAddress(
    state: AddressState,
    onAction: (AddressAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background).padding(horizontal = 12.dp),
    ) {
        StoreTextField(
            state = state.street,
            startIcon = null,
            endIcon = null,
            hint = stringResource(R.string.example_street),
            title = stringResource(R.string.street),
            imeAction = ImeAction.Next
        )
        StoreTextField(
            state = state.postalCode,
            startIcon = null,
            endIcon = if (state.isCorrectPostalCode) CheckIcon else null,
            hint = stringResource(R.string.example_postal_code),
            inputTransformation = InputTransformation.maxLengthInChars(5),
            title = stringResource(R.string.postal_code),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
        StoreTextField(
            state = state.state,
            startIcon = null,
            endIcon = null,
            hint = "",
            enable = false,
            title = stringResource(R.string.state)
        )
        StoreTextField(
            state = state.mayoralty,
            startIcon = null,
            endIcon = null,
            hint = "",
            enable = false,
            title = stringResource(R.string.mayoralty)
        )
        ExposedDropdownMenuSettlement(
            state = state,
            onAction = onAction
        )
        StoreTextField(
            state = state.numberContact,
            startIcon = PhoneIcon,
            endIcon = null,
            hint = stringResource(R.string.example_number),
            keyboardType = KeyboardType.Phone,
            inputTransformation = InputTransformation.maxLengthInChars(10),
            imeAction = ImeAction.Next,
            title = stringResource(R.string.number_contact)
        )
        StoreTextField(
            state = state.references,
            startIcon = null,
            endIcon = null,
            inputTransformation = InputTransformation.maxLengthInChars(128),
            hint = stringResource(R.string.example_reference),
            imeAction = ImeAction.None,
            lineLimits = TextFieldLineLimits.MultiLine(
                minHeightInLines = 3,
                maxHeightInLines = 4
            ),
            title = stringResource(R.string.reference)
        )
        Text(
            modifier = Modifier
                .align(Alignment.End),
            text = "${state.references.text.length}/128",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.4f
                )
            )
        )
        StoreActionButton(
            modifier = Modifier.padding(
                vertical = 12.dp
            ),
            text = "Agregar",
            isLoading = false
        ) {

        }
    }
}