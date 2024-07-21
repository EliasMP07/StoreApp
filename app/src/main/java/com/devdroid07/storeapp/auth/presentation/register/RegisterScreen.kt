@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.domain.usecases.UserDataValidator
import com.devdroid07.storeapp.auth.presentation.register.components.PasswordRequirement
import com.devdroid07.storeapp.core.presentation.designsystem.EmailIcon
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreLogoVertical
import com.devdroid07.storeapp.core.presentation.designsystem.components.StorePasswordTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField

@Composable
fun RegisterScreenRoot(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    RegisterScreen(
        state = state,
        onAction = onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
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
            text = "Registrate!", style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(text = "Please sign upp to continue our app")
        Spacer(modifier = Modifier.height(30.dp))
        StoreTextField(
            state = state.email,
            startIcon = EmailIcon,
            keyboardType = KeyboardType.Email,
            endIcon = if (state.isEmailValid) Icons.Default.Check else null,
            hint = stringResource(R.string.hint_text_email),
            title = stringResource(R.string.title_text_email)
        )
        Spacer(modifier = Modifier.height(10.dp))
        StorePasswordTextField(
            state = state.password,
            isPasswordVisible = state.isVisiblePassword,
            isSegurityPassword = state.passwordValidationState.isValidPassword,
            onTogglePasswordVisibility = {
                onAction(RegisterAction.OnToggleVisibilityPassword)
            },
            hint = stringResource(R.string.hint_text_password),
            title = stringResource(R.string.title_text_password)
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_characters,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = state.passwordValidationState.hasMinLength
        )
        Spacer(modifier = Modifier.height(4.dp))
        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_one_number,
            ),
            isValid = state.passwordValidationState.hasNumber
        )
        Spacer(modifier = Modifier.height(4.dp))
        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_lowercase_char,
            ),
            isValid = state.passwordValidationState.hasLowerCaseCharacter
        )
        Spacer(modifier = Modifier.height(4.dp))
        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_uppercase_char,
            ),
            isValid = state.passwordValidationState.hasUpperCaseCharacter
        )
        Spacer(modifier = Modifier.height(32.dp))
        StoreActionButton(
            enabled = state.canRegister,
            text = stringResource(R.string.text_btn_register), isLoading = state.isRegistering
        ) {
            onAction(RegisterAction.OnRegisterClick)
        }
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                append(stringResource(id = R.string.have_account) + " ")
                pushStringAnnotation(
                    tag = "clickable_text",
                    annotation = stringResource(id = R.string.sign_in)
                )
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    append(stringResource(id = R.string.sign_in))
                }
            }
        }
        ClickableText(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp),
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = "clickable_text",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onAction(RegisterAction.OnLoginClick)
                }
            }
        )

    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun RegisterScreenPreview() {
    StoreAppTheme {
        RegisterScreen(state = RegisterState()) {

        }
    }
}

