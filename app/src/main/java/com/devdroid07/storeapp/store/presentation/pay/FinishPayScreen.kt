@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.pay

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.store.presentation.pay.component.ErrorContentPay
import com.devdroid07.storeapp.store.presentation.pay.component.ErrorPay
import com.devdroid07.storeapp.store.presentation.pay.component.FinishPayContent
import com.devdroid07.storeapp.store.presentation.pay.component.TicketSuccessPay
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay

@Composable
fun FinishPayScreenRoot(
    viewModel: FinishPayViewModel,
    navigateToHome: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        when(state.pay){
            ProgressPay.FAILURE -> onBack()
            ProgressPay.SUCCESS -> navigateToHome()
            else -> onBack()
        }
    }

    FinishPayScreen(
        state = state,
        onAction = { action ->
            when (action) {
                FinishPayAction.GoToChangePaymentMethodClick -> onBack()
                FinishPayAction.OnBackClick -> onBack()
                FinishPayAction.OnNavigateHomeClick -> navigateToHome()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun FinishPayScreen(
    state: FinishPayState,
    onAction: (FinishPayAction) -> Unit,
) {

    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.confirm_pay_title_screen),
                openDrawer = {},
                onBack = {
                    onAction(FinishPayAction.OnBackClick)
                },
                isProfile = false,
                isMenu = false
            )
        }
    ) { paddingValue ->

        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            error = state.error,
            errorContent = {
                ErrorContentPay(
                    error = it,
                    onHomeClick = {
                        onAction(FinishPayAction.OnNavigateHomeClick)
                    }
                )
            },
        )

        if (result) {
            FinishPayContent(
                modifier = Modifier.padding(paddingValue),
                state = state,
                spacing = spacing,
                onAction = onAction
            )
        }

        if (state.isPaying) {
            CircularLoading()
        }

    }
    AnimatedVisibility(
        visible = state.pay == ProgressPay.FAILURE,
        enter = scaleIn(
            animationSpec = tween(1000)
        )
    ) {
        ErrorPay(
            onAction = onAction
        )
    }
    AnimatedVisibility(
        visible = state.pay == ProgressPay.SUCCESS,
        enter = scaleIn(
            animationSpec = tween(1000)
        )
    ) {
         TicketSuccessPay(
             state = state,
             onAction = onAction
         )
    }
}