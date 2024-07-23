@file:OptIn(ExperimentalFoundationApi::class,
            ExperimentalFoundationApi::class
)

package com.devdroid07.storeapp.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.auth.domain.validator.UserDataValidator
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
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
    private val authRepository: AuthRepository,
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

    private fun loading(isLoading: Boolean){
        _state.update {currentState ->
            currentState.copy(
                isLoggingIn = isLoading
            )
        }
    }


    private fun login(){
        viewModelScope.launch {
            loading(true)
            val result = authRepository.login(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            loading(false)
            when(result){
                is Result.Error -> {
                    when(result.error){
                        DataError.Network.UNAUTHORIZED -> {
                            eventChannel.send(LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            ))
                        }
                        DataError.Network.NOT_FOUND -> {
                            eventChannel.send(LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_no_found)
                            ))
                        }
                        else ->{
                            eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                        }
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}