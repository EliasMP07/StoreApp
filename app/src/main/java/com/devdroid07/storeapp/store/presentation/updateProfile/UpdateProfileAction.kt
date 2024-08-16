package com.devdroid07.storeapp.store.presentation.updateProfile

import com.devdroid07.storeapp.auth.presentation.register.RegisterAction

sealed interface UpdateProfileAction {
    data object OnBackClick: UpdateProfileAction
    data class SubmitCamaraPermissionInfo(
        val acceptedCamaraPermission: Boolean,
        val showCamaraRationale: Boolean
    ): UpdateProfileAction
    data object UpdateProfileClick: UpdateProfileAction
    data object OnToggleDialogSelectImage: UpdateProfileAction
    data object DismissRationaleDialog: UpdateProfileAction
    data class OnImageGalleryChange(val image: String): UpdateProfileAction
    data class OnImageCamaraChange(val image: String): UpdateProfileAction
}