@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.GoogleIcon
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreLogoVertical
import com.devdroid07.storeapp.core.presentation.designsystem.components.StorePasswordTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state by viewModel.state.collectAsState()

    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_LONG).show()
            }
            LoginEvent.OnLoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, "Login successfull", Toast.LENGTH_LONG).show()
            }
        }
    }
    LoginScreen(
        state = state,
        onAction = { action ->
            when(action){
                LoginAction.OnRegisterClick -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            StoreLogoVertical()
        }
        Text(
            text = "Welcome!", style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(text = "Please login or sign up to continue our app")
        Spacer(modifier = Modifier.height(30.dp))
        StoreTextField(
            state = state.email,
            startIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            endIcon = if (state.passwordCorrect) Icons.Default.Check else null,
            hint = "example@test.com",
            title = "Email"
        )
        Spacer(modifier = Modifier.height(10.dp))
        StorePasswordTextField(
            state = state.password,
            isPasswordVisible = state.isVisiblePassword,
            onTogglePasswordVisibility = {
                onAction(LoginAction.OnToggleVisibilityPassword)
            },
            hint = "Password",
            title = "Password"
        )
        Spacer(modifier = Modifier.height(10.dp))
        StoreActionButton(
            enabled = state.canLogin,
            text = "Login", isLoading = state.isLoggingIn
        ) {

        }
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append(stringResource(id = R.string.dont_have_an_account) + " ")
                pushStringAnnotation(
                    tag = "clickable_text",
                    annotation = stringResource(id = R.string.sign_up)
                )
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    append(stringResource(id = R.string.sign_up))
                }
            }
        }
        ClickableText(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(20.dp),
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onAction(LoginAction.OnRegisterClick)
                }
            }
        )

    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun LoginScreenPreview() {
    StoreAppTheme {
        LoginScreen(
            state = (LoginState()),
            onAction = {}
        )
    }
}

