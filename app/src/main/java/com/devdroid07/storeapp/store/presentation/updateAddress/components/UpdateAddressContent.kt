@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.updateAddress.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.PhoneIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressAction
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressState

@Composable
fun UpdateAddressContent(
    modifier: Modifier = Modifier,
    state: UpdateAddressState,
    spacing: Dimensions,
    onAction: (UpdateAddressAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = spacing.spaceMedium,
                vertical = spacing.spaceMedium
            ),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
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

        Text(
            modifier = Modifier
                .align(Alignment.End),
            text = "${state.postalCode.text.length}/5",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.4f
                )
            )
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

        ExposedDropdownMenuSettlementUpdate(
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
                vertical = spacing.spaceSmall
            ),
            text = stringResource(R.string.add_address),
            enabled = true,
            isLoading = state.isUpdatingAddress
        ) {
            onAction(UpdateAddressAction.OnUpdateAddress)
        }

    }
}
