@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.domain.usecases.AuthUseCases
import com.devdroid07.storeapp.auth.domain.validator.UserDataValidator
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.core.presentation.ui.util.imageCamara
import com.devdroid07.storeapp.core.presentation.ui.util.reduceImageSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _state.value.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                _state.update {
                    it.copy(
                        isEmailValid = isValidEmail,
                        canRegister = isValidEmail && _state.value.passwordValidationState.isValidPassword
                                && !_state.value.isRegistering && _state.value.name.text.isNotEmpty() && _state.value.lastName.text.isNotEmpty()
                    )
                }
            }
            .launchIn(viewModelScope)

        _state.value.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                _state.update {
                    it.copy(
                        passwordValidationState = passwordValidationState,
                        canRegister = _state.value.isEmailValid && passwordValidationState.isValidPassword
                                && !_state.value.isRegistering && _state.value.name.text.isNotEmpty() && _state.value.lastName.text.isNotEmpty()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(
        action: RegisterAction,
    ) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnToggleVisibilityPassword -> {
                _state.update { it.copy(isVisiblePassword = !it.isVisiblePassword)}
            }
            RegisterAction.DismissRationaleDialog -> {
                _state.update { it.copy(showCamaraRationale = false) }
            }
            RegisterAction.OnToggleDialogSelectImage -> {
                _state.update { it.copy(showOptionProfileImage = !it.showOptionProfileImage) }
            }
            is RegisterAction.SubmitCamaraPermissionInfo -> {
                _state.update {
                    it.copy(
                        showCamaraRationale = action.showCamaraRationale
                    )
                }
            }
            is RegisterAction.OnImageCamaraChange -> {
                onImageCamaraChange(action.image)
            }
            is RegisterAction.OnImageGalleryChange -> {
                onImageGalleryChange(action.image)
            }
            else -> Unit
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

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }
            val result = authUseCases.signUpWithEmailUseCase(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString(),
                image = _state.value.image,
                name = _state.value.name.text.toString(),
                lastname = _state.value.lastName.text.toString(),
            )
            _state.update { it.copy(isRegistering = false) }
            when (result) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.UNAUTHORIZED -> {
                            eventChannel.send(
                                RegisterEvent.Error(
                                    UiText.StringResource(R.string.error_email_exist)
                                )
                            )
                        }
                        else -> {
                            eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                        }
                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegisterSuccess)
                }
            }

        }
    }
}