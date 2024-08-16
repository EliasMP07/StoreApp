@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.domain.validator.UserDataValidator
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.RegisterState
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.EmailIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.PersonIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StorePasswordTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField

@Composable
fun RegisterContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .padding(spacing.spaceMedium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_splashscreen),
                contentDescription = stringResource(R.string.content_description_logo_app)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column {
                Text(
                    text = stringResource(R.string.title_register_screen),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.message_register_screen),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.photo_profile)
        )
        PhotoProfile(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            image = state.imagePreview,
            onClick = {
                onAction(RegisterAction.OnToggleDialogSelectImage)
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        StoreTextField(
            state = state.email,
            startIcon = EmailIcon,
            keyboardType = KeyboardType.Email,
            endIcon = if (state.isEmailValid) Icons.Default.Check else null,
            hint = stringResource(R.string.hint_text_email),
            title = stringResource(R.string.title_text_email)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        StoreTextField(
            state = state.name,
            startIcon = PersonIcon,
            keyboardType = KeyboardType.Text,
            endIcon = null,
            hint = "",
            title = stringResource(R.string.title_text_name)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        StoreTextField(
            state = state.lastName,
            startIcon = PersonIcon,
            keyboardType = KeyboardType.Text,
            endIcon = null,
            hint = "",
            title = stringResource(R.string.title_text_lastname)
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
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
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_characters,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = state.passwordValidationState.hasMinLength
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_one_number,
            ),
            isValid = state.passwordValidationState.hasNumber
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_lowercase_char,
            ),
            isValid = state.passwordValidationState.hasLowerCaseCharacter
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_uppercase_char,
            ),
            isValid = state.passwordValidationState.hasUpperCaseCharacter
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        StoreActionButton(
            enabled = state.canRegister,
            text = stringResource(R.string.text_btn_register),
            isLoading = state.isRegistering
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