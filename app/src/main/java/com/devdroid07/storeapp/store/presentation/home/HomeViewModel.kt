package com.devdroid07.storeapp.store.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.Result
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
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            storeUseCases.getAllProducts().collect { result ->
                when(result){
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                error = result.error.name,
                                isLoading = false
                            )
                        }
                    }
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                error = null,
                                products = result.data,
                                productRecomended = result.data.first {
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
