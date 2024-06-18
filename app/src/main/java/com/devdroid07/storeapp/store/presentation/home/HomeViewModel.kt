package com.devdroid07.storeapp.store.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.store.domain.StoreRepository
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> get() = _state.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getAllProduct().collect { response ->
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
                                productRecomended = response.data.filter {
                                    it.ratingRate >= 4.0
                                }.first(),
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
