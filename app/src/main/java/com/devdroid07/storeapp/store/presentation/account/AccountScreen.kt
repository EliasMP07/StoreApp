@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.account

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.components.PhotoProfile
import com.devdroid07.storeapp.core.presentation.designsystem.EmailIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StorePasswordTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.store.presentation.myCart.MyCartAction

@Composable
fun AccountScreenRoot(
    state: AccountState,
    openDrawer: () -> Unit
) {
    AccountScreen(
        state = state,
        onAction = { action ->
            when (action) {
                AccountAction.OpenDrawer -> openDrawer()
            }
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
                isProfile = false,
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
                hint = stringResource(R.string.hint_text_email),
                title = stringResource(R.string.title_text_email)
            )
        }
    }
}
//
//@Preview
//@Composable
//private fun AccountScreenPreview() {
//    StoreAppTheme {
//        AccountScreen(state = AccountState())
//    }
//}