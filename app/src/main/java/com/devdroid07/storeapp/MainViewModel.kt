package com.devdroid07.storeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.SessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    isCheckingAuth = true
                )
            }
            _state.update {
                it.copy(
                    isLoggedIn = sessionStorage.get() != null
                )
            }
            _state.update {currentState ->
                currentState.copy(
                    isCheckingAuth = false
                )
            }
        }
    }
}