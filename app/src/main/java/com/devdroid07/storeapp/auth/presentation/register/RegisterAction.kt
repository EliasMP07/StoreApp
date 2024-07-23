package com.devdroid07.storeapp.auth.presentation.register

sealed interface RegisterAction {
    data object OnToggleVisibilityPassword : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
    data class SubmitCamaraPermissionInfo(
        val acceptedCamaraPermission: Boolean,
        val showCamaraRationale: Boolean
    ): RegisterAction
    data object OnToggleDialogSelectImage: RegisterAction
    data object DismissRationaleDialog: RegisterAction
    data class OnImageGalleryChange(val image: String): RegisterAction
    data class OnImageCamaraChange(val image: String): RegisterAction
}