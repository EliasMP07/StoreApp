@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.components.PhotoProfile
import com.devdroid07.storeapp.core.presentation.designsystem.EmailIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.PersonIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar

@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel,
    drawerState: DrawerState,
    onLogout: () -> Unit,
    closeDrawer:() -> Unit,
    navigateToUpdateProfile: () -> Unit,
    openDrawer: () -> Unit,
    onBack: () -> Unit
) {

    BackHandler {
        if (drawerState.isOpen) {
            closeDrawer()
        } else {
            onBack()
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    AccountScreen(
        state = state,
        onAction = { action ->
            when (action) {
                AccountAction.OnLogoutClick -> onLogout()
                AccountAction.OpenDrawer -> openDrawer()
                AccountAction.OnUpdateProfile -> navigateToUpdateProfile()
            }
            viewModel.onAction(action)
        })
}

@Composable
private fun AccountScreen(
    state: AccountState,
    onAction: (AccountAction) -> Unit
) {
    val spacing = LocalSpacing.current
    Scaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.profile_title_screen),
                isMenu = true,
                openDrawer = { 
                    onAction(AccountAction.OpenDrawer)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(spacing.spaceMedium)
        ) {
            PhotoProfile(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isClickeable = false,
                image = state.user?.image.orEmpty(),
                onClick = {

                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            StoreTextField(
                state = TextFieldState(state.user?.email.orEmpty()),
                startIcon = EmailIcon,
                keyboardType = KeyboardType.Email,
                endIcon = null,
                enable = false,
                hint = stringResource(R.string.hint_text_email),
                title = stringResource(R.string.title_text_email)
            )
            StoreTextField(
                state = TextFieldState(state.user?.name.orEmpty()),
                startIcon = PersonIcon,
                enable = false,
                keyboardType = KeyboardType.Text,
                endIcon = null,
                hint = "",
                title = stringResource(R.string.title_text_name)
            )
            StoreTextField(
                state = TextFieldState(state.user?.lastname.orEmpty()),
                startIcon = PersonIcon,
                enable = false,
                keyboardType = KeyboardType.Text,
                endIcon = null,
                hint = "",
                title = stringResource(R.string.title_text_lastname)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            StoreActionButton(
                text = stringResource(R.string.btn_text_update_profile),
                isLoading = false
            ) {
                onAction(AccountAction.OnUpdateProfile)
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            StoreActionButtonOutline(
                text = stringResource(R.string.btn_text_logout),
                isLoading = false
            ) {
                onAction(AccountAction.OnLogoutClick)
            }
        }
    }
}
