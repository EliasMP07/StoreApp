@file:OptIn(
    ExperimentalFoundationApi::class
)

package com.devdroid07.storeapp.store.presentation.updateProfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.insert
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.RegisterEvent
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.core.presentation.ui.util.imageCamara
import com.devdroid07.storeapp.core.presentation.ui.util.reduceImageSize
import com.devdroid07.storeapp.store.domain.usecases.account.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val accountUseCases: AccountUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateProfileState())
    val state: StateFlow<UpdateProfileState> get() = _state.asStateFlow()


    private val eventChannel = Channel<UpdateProfileEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            val user = sessionStorage.get()
            user?.let { userInfo ->
                _state.update {
                    it.copy(
                        imagePreview = userInfo.image,
                        image = userInfo.image,
                        name = it.name.apply {
                            edit {
                                insert(
                                    0,
                                    userInfo.name
                                )
                            }
                        },
                        lastname = it.lastname.apply {
                            edit {
                                insert(
                                    0,
                                    userInfo.lastname
                                )
                            }
                        },
                    )
                }
            }
        }
    }

    fun onAction(action: UpdateProfileAction) {
        when (action) {
            UpdateProfileAction.DismissRationaleDialog -> {
                _state.update { it.copy(showCamaraRationale = false) }
            }
            UpdateProfileAction.OnToggleDialogSelectImage -> {
                _state.update { it.copy(showOptionProfileImage = !it.showOptionProfileImage) }
            }
            is UpdateProfileAction.SubmitCamaraPermissionInfo -> {
                _state.update {
                    it.copy(
                        showCamaraRationale = action.showCamaraRationale
                    )
                }
            }
            is UpdateProfileAction.OnImageCamaraChange -> {
                onImageCamaraChange(action.image)
            }
            is UpdateProfileAction.OnImageGalleryChange -> {
                onImageGalleryChange(action.image)
            }
            UpdateProfileAction.UpdateProfileClick -> updateProfile()
            else -> Unit
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isUpdatingProfile = true) }
            val result = accountUseCases.updateProfileUseCase(
                name = _state.value.name.text.toString(),
                lastName = _state.value.lastname.text.toString(),
                image = _state.value.image
            )
            _state.update { it.copy(isUpdatingProfile = false) }
            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        UpdateProfileEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    eventChannel.send(
                        UpdateProfileEvent.Success
                    )
                }
            }
        }
    }

    private fun onImageCamaraChange(image: String) {
        _state.update {
            it.copy(
                showOptionProfileImage = !it.showOptionProfileImage,
                imagePreview = image,
                image = imageCamara(image)
            )
        }
    }

    private fun onImageGalleryChange(image: String) {
        _state.update {
            it.copy(
                showOptionProfileImage = !it.showOptionProfileImage,
                imagePreview = image,
                image = reduceImageSize(image)
            )
        }
    }

}