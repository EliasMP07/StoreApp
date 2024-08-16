@file:OptIn(ExperimentalFoundationApi::class,
            ExperimentalMaterial3Api::class
)

package com.devdroid07.storeapp.store.presentation.updateProfile

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.components.PhotoProfile
import com.devdroid07.storeapp.auth.presentation.register.requestStoreProfilePhoto
import com.devdroid07.storeapp.core.presentation.designsystem.EmailIcon
import com.devdroid07.storeapp.core.presentation.designsystem.LocalSpacing
import com.devdroid07.storeapp.core.presentation.designsystem.PersonIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.CircularLoading
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreDialog
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreTextField
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.ui.util.ComposeFileProvider
import com.devdroid07.storeapp.core.presentation.ui.util.hasCamaraPermission
import com.devdroid07.storeapp.core.presentation.ui.util.shouldShowCamaraPermissionRationale
import com.devdroid07.storeapp.store.presentation.account.AccountAction
import com.devdroid07.storeapp.store.presentation.account.AccountState
import com.devdroid07.storeapp.store.presentation.updateProfile.components.PhotoProfileUpdate

@Composable
fun UpdateProfileScreenRoot(
    viewModel: UpdateProfileViewModel,
    context: Context,
    onBack: () -> Unit
){
    val focusManager = LocalFocusManager.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        onBack()
    }

    ObserveAsEvents(viewModel.events) {event->

        when(event){
            is UpdateProfileEvent.Error -> {
                focusManager.clearFocus()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }
            UpdateProfileEvent.Success -> {
                focusManager.clearFocus()
                onBack()
            }
        }
    }

    UpdateProfileScreen(
        state = state,
        context = context,
        onAction = {action ->
            when(action){
                UpdateProfileAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun UpdateProfileScreen(
    context: Context,
    state: UpdateProfileState,
    onAction: (UpdateProfileAction) -> Unit
) {
    // Configura el lanzador para tomar una foto utilizando la cámara.
    val intentCamaraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                // Si se toma una foto, convierte el bitmap en una ruta de archivo.
                val image = ComposeFileProvider.getPathFromBitmap(
                    context,
                    it // Bitmap de la imagen tomada con la cámara.
                )
                // Guarda la imagen en los estados.
                onAction(UpdateProfileAction.OnImageCamaraChange(image))
            }
        }



    // Configura el lanzador para solicitar el permiso de cámara.
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { hasCamaraPermission ->
            // Almacena el resultado de la solicitud de permiso (si se concedió o no).
            val activity = context as ComponentActivity
            // Verifica si se debe mostrar una justificación para el permiso de cámara.
            val showCamaraRationale = activity.shouldShowCamaraPermissionRationale()

            // Guarda la informacion del permiso en los estados.
            onAction(
                UpdateProfileAction.SubmitCamaraPermissionInfo(
                    acceptedCamaraPermission = hasCamaraPermission, // Indica si el permiso fue aceptado.
                    showCamaraRationale = showCamaraRationale // Indica si se debe mostrar la justificación del permiso.
                )
            )

            // Si el permiso de cámara fue concedido, lanza el intent para usar la cámara.
            if (hasCamaraPermission) {
                intentCamaraLauncher.launch()
            }
        }



    // Configura el lanzador para seleccionar una imagen de la galería.
    val intentGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // Si se selecciona una imagen, crea un archivo a partir del URI seleccionado.
            val image = ComposeFileProvider.createFileFromUri(
                context, // Contexto actual.
                it // URI de la imagen seleccionada.
            )
            // Guarda la imagen en los estados
            onAction(UpdateProfileAction.OnImageGalleryChange(image.toString()))
        }
    }

    val spacing = LocalSpacing.current
    Scaffold(
        topBar = {
            StoreToolbar(
                title = stringResource(R.string.update_profile_title_screen),
                isMenu = false,
                onBack = {
                    onAction(UpdateProfileAction.OnBackClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(spacing.spaceMedium)
        ) {
            PhotoProfileUpdate(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                image = state.imagePreview,
                onClick = {
                    onAction(UpdateProfileAction.OnToggleDialogSelectImage)
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            StoreTextField(
                state = state.name,
                startIcon = PersonIcon,
                keyboardType = KeyboardType.Text,
                endIcon = null,
                hint = "",
                title = stringResource(R.string.title_text_name)
            )
            StoreTextField(
                state = state.lastname,
                startIcon = PersonIcon,
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
                onAction(UpdateProfileAction.UpdateProfileClick)
            }
        }
    }
    if (state.isUpdatingProfile){
        CircularLoading()
    }
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
                        onAction(UpdateProfileAction.DismissRationaleDialog)
                        permissionLauncher.requestStoreProfilePhoto(context)
                    },
                )
            }
        )
    }
    if (state.showOptionProfileImage) {
        StoreDialog(
            title = stringResource(R.string.title_photo_profile_dialog),
            onDismiss = {
                onAction(UpdateProfileAction.OnToggleDialogSelectImage)
            },
            description = stringResource(R.string.description_text_profile_photo_dialog),
            primaryButton = {
                StoreActionButtonOutline(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_text_camara),
                    isLoading = false,
                    onClick = {
                        if (context.hasCamaraPermission()) {
                            intentCamaraLauncher.launch()
                        }else{
                            permissionLauncher.requestStoreProfilePhoto(context)
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
