package com.devdroid07.storeapp.store.presentation.home


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel()
class HomeViewModel @Inject constructor(

): ViewModel(){

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state.asStateFlow()

    fun onAction(action: HomeAction){
        when(action){

            else -> {}
        }
    }
}