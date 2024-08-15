package com.devdroid07.storeapp.auth.presentation.register

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.components.RegisterForm
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreDialog
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.ui.util.ComposeFileProvider
import com.devdroid07.storeapp.core.presentation.ui.util.hasCamaraPermission
import com.devdroid07.storeapp.core.presentation.ui.util.shouldShowCamaraPermissionRationale

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel,
    context: Context,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegisterSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    context.getString(R.string.account_create_success),
                    Toast.LENGTH_LONG
                ).show()
                navigateToHome()
            }
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            val hasCamaraPermission = it
            val activity = context as ComponentActivity
            val showCamaraRationale = activity.shouldShowCamaraPermissionRationale()
            viewModel.onAction(
                RegisterAction.SubmitCamaraPermissionInfo(
                    acceptedCamaraPermission = hasCamaraPermission,
                    showCamaraRationale = showCamaraRationale
                )
            )
        }

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showCamaraRationale = activity.shouldShowCamaraPermissionRationale()

        viewModel.onAction(
            RegisterAction.SubmitCamaraPermissionInfo(
                acceptedCamaraPermission = context.hasCamaraPermission(),
                showCamaraRationale = showCamaraRationale
            )
        )

        if (!showCamaraRationale) {
            permissionLauncher.requestStoreProfilePhoto(context)
        }
    }

    RegisterScreen(
        state = state,
        context = context,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> {
                    navigateToRegister()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

    if (state.showCamaraRationale) {
        StoreDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { },
            description = stringResource(R.string.text_permission_required_camara),
            primaryButton = {
                StoreActionButtonOutline(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        viewModel.onAction(RegisterAction.DismissRationaleDialog)
                        permissionLauncher.requestStoreProfilePhoto(context)
                    },
                )
            }
        )
    }

}

@Composable
private fun RegisterScreen(
    context: Context,
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {

    val intentCamaraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                val image = ComposeFileProvider.getPathFromBitmap(
                    context,
                    it
                )
                onAction(RegisterAction.OnImageCamaraChange(image))
            }
        }

    val intentGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        it?.let {
            val image = ComposeFileProvider.createFileFromUri(
                context,
                it
            )
            onAction(RegisterAction.OnImageGalleryChange(image.toString()))
        }
    }

    RegisterForm(
        state = state,
        onAction = onAction
    )


    if (state.showOptionProfileImage) {
        StoreDialog(
            title = stringResource(R.string.title_photo_profile_dialog),
            onDismiss = {
                onAction(RegisterAction.OnToggleDialogSelectImage)
            },
            description = stringResource(R.string.description_text_profile_photo_dialog),
            primaryButton = {
                StoreActionButtonOutline(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_text_camara),
                    isLoading = false,
                    onClick = {
                        if (state.hasPermissionCamara) {
                            intentCamaraLauncher.launch()
                        }
                    },
                )
            },
            secondaryButton = {
                StoreActionButtonOutline(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_text_gallery),
                    isLoading = false,
                    onClick = {
                        intentGallery.launch("image/*")
                    },
                )
            }
        )
    }
}

private fun ActivityResultLauncher<String>.requestStoreProfilePhoto(context: Context) {
    val hasCamaraPermision = context.hasCamaraPermission()
    val camaraPermission = Manifest.permission.CAMERA
    if (!hasCamaraPermision) {
        launch(camaraPermission)
    }
}
