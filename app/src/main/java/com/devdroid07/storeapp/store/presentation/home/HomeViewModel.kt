package com.devdroid07.storeapp.store.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.domain.model.Response
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases,
    private val sessionStorage: SessionStorage,
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    user = sessionStorage.get()
                )
            }
        }
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            storeUseCases.getAllProducts().collect { response ->
                when (response) {
                    is Response.Failure -> {
                        _state.update {
                            it.copy(
                                error = response.exception?.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }

                    Response.Loading -> {
                        _state.update {
                            it.copy(
                                error = null,
                                isLoading = true
                            )
                        }
                    }

                    is Response.Success -> {
                        _state.update {
                            it.copy(
                                error = null,
                                products = response.data,
                                productRecomended = response.data.first {
                                    it.ratingRate >= 4.0
                                },
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.RetryClick -> loadProducts()
            else -> Unit
        }
    }
}
