@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.updateAddress

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.ErrorContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.handleResultView
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.store.presentation.updateAddress.components.UpdateAddressContent

@Composable
fun UpdateAddressScreenRoot(
    context: Context,
    viewModel: UpdateAddressViewModel,
    onBack: () -> Unit,
    onSuccessUpdate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        onBack()
    }

    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            is UpdateAddressEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            UpdateAddressEvent.Success -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    context.getString(R.string.success_update_address),
                    Toast.LENGTH_LONG
                ).show()
                onSuccessUpdate()
            }
        }
    }

    UpdateAddressScreen(
        state = state,
        onAction = { action ->
            when(action){
                UpdateAddressAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun UpdateAddressScreen(
    state: UpdateAddressState,
    onAction: (UpdateAddressAction) -> Unit,
) {
    val spacing = LocalSpacing.current
    Scaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.update_address_title_screen),
                isMenu = false,
                onBack = {
                    onAction(UpdateAddressAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        val result = handleResultView(
            isLoading = state.isLoading,
            contentLoading = {
                CircularLoading()
            },
            errorContent = {
                ErrorContent(error = it)
            },
            error = state.error
        )
        if (result) {
            UpdateAddressContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                spacing = spacing,
                onAction = onAction

            )
        }

    }
}