@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.auth.presentation.login

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator

): ViewModel() {

    private var _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(state.value.email.textAsFlow(), state.value.password.textAsFlow()){ email, password ->
            _state.update {
                it.copy(
                    passwordCorrect = userDataValidator.isValidEmail(email = email.toString().trim()),
                    canLogin = userDataValidator.isValidEmail(email = email.toString().trim()) && password.isNotEmpty()
                )
            }
        }.launchIn(viewModelScope)
    }
    fun onAction(
        action: LoginAction
    ){
        when(action){
            LoginAction.OnLoginClick -> login()
            LoginAction.OnToggleVisibilityPassword -> {
                _state.update {
                    it.copy(
                        isVisiblePassword = !_state.value.isVisiblePassword
                    )
                }
            }
            else -> Unit
        }
    }

    private fun login(){
        viewModelScope.launch {

        }
    }
}