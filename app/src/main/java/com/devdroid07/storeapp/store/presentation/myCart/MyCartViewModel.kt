package com.devdroid07.storeapp.store.presentation.myCart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyCartViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(MyCartState())
    val state: StateFlow<MyCartState> get() = _state.asStateFlow()

    fun onAction(
        action: MyCartAction
    ){

    }
}