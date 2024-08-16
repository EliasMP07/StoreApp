package com.devdroid07.storeapp.auth.presentation.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.register.components.RegisterContent
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButtonOutline
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreDialog
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreSnackBar
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.core.presentation.ui.util.ComposeFileProvider
import com.devdroid07.storeapp.core.presentation.ui.util.hasCamaraPermission
import com.devdroid07.storeapp.core.presentation.ui.util.shouldShowCamaraPermissionRationale
import kotlinx.coroutines.launch

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel,
    context: Context,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = event.message.asString(context))
                }
                focusManager.clearFocus()
            }
            RegisterEvent.RegisterSuccess -> {
                focusManager.clearFocus()
                Toast.makeText(
                    context,
                    context.getString(R.string.account_create_success),
                    Toast.LENGTH_SHORT
                ).show()
                navigateToHome()
            }
        }
    }

    RegisterScreen(
        state = state,
        context = context,
        snackbarHostState = snackbarHostState,
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

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun RegisterScreen(
    context: Context,
    state: RegisterState,
    snackbarHostState: SnackbarHostState,
    onAction: (RegisterAction) -> Unit,
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
                onAction(RegisterAction.OnImageCamaraChange(image))
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
                RegisterAction.SubmitCamaraPermissionInfo(
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
            onAction(RegisterAction.OnImageGalleryChange(image.toString()))
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                StoreSnackBar(snackbarData = it)
            }
        }
    ) {
        RegisterContent(
            state = state,
            onAction = onAction
        )
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
                        onAction(RegisterAction.DismissRationaleDialog)
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
                onAction(RegisterAction.OnToggleDialogSelectImage)
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

/**
 * Solicita el permiso de la cámara para capturar la foto de perfil de la tienda si no se ha otorgado previamente.
 *
 * @param context El contexto de la actividad
 */
private fun ActivityResultLauncher<String>.requestStoreProfilePhoto(context: Context) {
    val hasCamaraPermision = context.hasCamaraPermission() // Verifica si el permiso de la cámara ya está otorgado
    val camaraPermission = Manifest.permission.CAMERA // Define el permiso de la cámara
    if (!hasCamaraPermision) {
        launch(camaraPermission)// Solicita el permiso de la cámara si aún no
    }
}
