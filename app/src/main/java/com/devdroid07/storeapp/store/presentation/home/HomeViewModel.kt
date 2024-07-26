package com.devdroid07.storeapp.store.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases,
    private val sessionStorage: SessionStorage,
) : ViewModel(){

    private var _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> get() = _state.asStateFlow()

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

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

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.RetryClick -> loadProducts()
            is HomeAction.AddFavoriteClick -> {
                addFavorite(action.productId)
            }
            is HomeAction.RemoveFavoriteClick -> {
                removeFavorite(action.productId)
            }
            else -> Unit
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            storeUseCases.getAllProducts().collect { result ->
                _state.update { currentState ->
                    when (result) {
                        is Result.Error -> {
                            currentState.copy(
                                error = result.error.asUiText(),
                                isLoading = false
                            )
                        }
                        is Result.Success -> {
                            currentState.copy(
                                error = null,
                                products = result.data,
                                productRecomended = result.data.firstOrNull { product ->
                                    product.ratingRate > 4.0
                                } ?: Product(),
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }

    private fun addFavorite(idProduct: String) {
        viewModelScope.launch {
            val result = storeUseCases.addFavoriteProductUseCase(idProduct)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        HomeEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id.toString() == idProduct) {
                                product.copy(isFavorite = true)
                            } else {
                                product
                            }
                        }
                        currentState.copy(
                            products = updatedProducts
                        )
                    }
                    eventChannel.send(
                        HomeEvent.Success(UiText.StringResource(R.string.success_add_favorite))
                    )
                }
            }
        }
    }

    private fun removeFavorite(idProduct: String) {
        viewModelScope.launch {

            val result = storeUseCases.removeFavoriteProductUseCase(idProduct)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        HomeEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id.toString() == idProduct) {
                                product.copy(isFavorite = false)
                            } else {
                                product
                            }
                        }
                        currentState.copy(
                            products = updatedProducts
                        )
                    }
                    eventChannel.send(
                        HomeEvent.Success(UiText.StringResource(R.string.success_delete_favorite))
                    )
                }
            }
        }
    }

}
