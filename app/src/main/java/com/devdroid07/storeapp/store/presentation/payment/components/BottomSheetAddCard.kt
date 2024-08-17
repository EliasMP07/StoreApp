@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.payment.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.CreditCard
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.isValidCreditCard
import com.devdroid07.storeapp.core.presentation.ui.ExpiryDateInputTransformation
import com.devdroid07.storeapp.core.presentation.ui.TextCardTransformation
import com.devdroid07.storeapp.store.presentation.payment.PaymentAction
import com.devdroid07.storeapp.store.presentation.payment.PaymentState

@Composable
fun BottomSheetAddCard(
    modifier: Modifier = Modifier,
    state: PaymentState,
    spacing: Dimensions,
    onAction: (PaymentAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = spacing.spaceMedium
            ),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)

    ) {

        CreditCard(
            cardNumber = state.numberCard.text.toString(),
            cvv = state.cvv.text.toString(),
            nameHeadline = state.nameHeadlineCard.text.toString(),
            dateExpire = state.expireDate.text.toString()
        )

        StoreTextField(
            state = state.nameHeadlineCard,
            startIcon = null,
            endIcon = null,
            hint = "",
            title = stringResource(R.string.headlinecard),
            imeAction = ImeAction.Next
        )
        StoreTextField(
            state = state.numberCard,
            startIcon = null,
            endIcon = if (isValidCreditCard(
                    state.numberCard.text.toString().replace(
                        " ",
                        ""
                    )
                )
            ) CheckIcon else null,
            hint = "",
            inputTransformation = TextCardTransformation(),
            title = stringResource(R.string.number_tarjet),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            StoreTextField(
                modifier = Modifier.weight(0.5f),
                state = state.cvv,
                startIcon = null,
                endIcon = null,
                hint = "",
                keyboardType = KeyboardType.Number,
                inputTransformation = InputTransformation.maxLengthInChars(3),
                title = stringResource(R.string.cvv)
            )
            StoreTextField(
                modifier = Modifier.weight(0.5f),
                state = state.expireDate,
                startIcon = null,
                endIcon = null,
                hint = "",
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardType = KeyboardType.Number,
                inputTransformation = ExpiryDateInputTransformation(),
                imeAction = ImeAction.Done,
                title = stringResource(R.string.expires)
            )
        }

        StoreActionButton(
            modifier = Modifier.padding(
                vertical = spacing.spaceSmall
            ),
            enabled = state.canCreateCard,
            text = stringResource(R.string.add_address),
            isLoading = state.isCreatingCard
        ) {
            focusManager.clearFocus()
            onAction(PaymentAction.OnCreateCardClick)
        }

    }
}