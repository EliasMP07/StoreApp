@file:OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)

package com.devdroid07.storeapp.auth.presentation.register

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.components.RegisterForm
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreDialog
import com.devdroid07.storeapp.core.presentation.ui.util.ComposeFileProvider
import com.devdroid07.storeapp.core.presentation.ui.util.hasCamaraPermission
import com.devdroid07.storeapp.core.presentation.ui.util.shouldShowCamaraPermissionRationale

@Composable
fun RegisterScreenRoot(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {

            val hasCamaraPermission = it
            val activity = context as ComponentActivity
            val showCamaraRationale = activity.shouldShowCamaraPermissionRationale()

            onAction(
                RegisterAction.SubmitCamaraPermissionInfo(
                    acceptedCamaraPermission = hasCamaraPermission,
                    showCamaraRationale = showCamaraRationale
                )
            )
        }

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showCamaraRationale = activity.shouldShowCamaraPermissionRationale()

        onAction(
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
        onAction = onAction
    )

    if (state.showCamaraRationale) {
        StoreDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = "Se requiere el permiso para la foto de perfil",
            primaryButton = {
                StoreActionButtonOutline(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(RegisterAction.DismissRationaleDialog)
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
    onAction: (RegisterAction) -> Unit
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
            title = "Sefs ",
            onDismiss = {
                onAction(RegisterAction.OnToggleDialogSelectImage)
            },
            description = "Seleciona una opción",
            primaryButton = {
                StoreActionButtonOutline(
                    modifier = Modifier.weight(1f),
                    text = "Camara",
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
                    text = "Galeria",
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
