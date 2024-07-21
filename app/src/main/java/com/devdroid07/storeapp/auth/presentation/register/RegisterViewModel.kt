@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.auth.domain.usecases.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _state.value.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                _state.update { currentState ->
                    currentState.copy(
                        isEmailValid = isValidEmail,
                        canRegister = isValidEmail && state.value.passwordValidationState.isValidPassword
                                && !state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)

        _state.value.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                _state.update {currentState ->
                    currentState.copy(
                        passwordValidationState = passwordValidationState,
                        canRegister = _state.value.isEmailValid && passwordValidationState.isValidPassword
                                && !state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(
        action: RegisterAction
    ){
        when(action){
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnToggleVisibilityPassword -> {
                _state.update {currentState ->
                    currentState.copy(
                        isVisiblePassword = !currentState.isVisiblePassword
                    )
                }
            }
            else -> Unit
        }
    }
    private fun register(){

    }
}