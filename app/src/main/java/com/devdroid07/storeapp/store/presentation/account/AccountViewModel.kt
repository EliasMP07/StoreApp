package com.devdroid07.storeapp.store.presentation.account

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
class AccountViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel(){

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {accountState ->
                accountState.copy(
                    user = sessionStorage.get()
                )
            }
        }
    }
}