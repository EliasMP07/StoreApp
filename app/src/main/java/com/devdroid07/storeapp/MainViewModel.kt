package com.devdroid07.storeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
        }
    }
}