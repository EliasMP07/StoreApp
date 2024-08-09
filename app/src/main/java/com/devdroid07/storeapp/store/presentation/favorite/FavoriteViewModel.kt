package com.devdroid07.storeapp.store.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state.asStateFlow()

    private val eventChannel = Channel<FavoriteEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getAllFavorites()
    }

    fun onAction(
        action: FavoriteAction
    ){
        when(action){
            is FavoriteAction.RemoveFavoriteSlide -> removeFavorite(action.productId)
            FavoriteAction.RetryClick -> getAllFavorites()
            else -> Unit
        }
    }

    private fun getAllFavorites(){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }
            productUseCases.getFavoritesProductsUseCase().collectLatest { result ->
                _state.update { currentState ->
                    when (result) {
                        is Result.Error -> {
                            currentState.copy(
                                isLoading = false,
                                error = result.error.asUiText()
                            )

                        }
                        is Result.Success -> {
                            currentState.copy(
                                productFavorites = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun removeFavorite(idProduct: String) {
        viewModelScope.launch {

            when (val result = productUseCases.removeFavoriteProductUseCase(idProduct)) {
                is Result.Error -> {
                    eventChannel.send(
                        FavoriteEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    val newProductsFavorites = _state.value.productFavorites.toMutableList()
                    newProductsFavorites.removeIf {
                        it.id.toString() == idProduct
                    }
                    _state.update { currentState ->
                        currentState.copy(
                            productFavorites = newProductsFavorites
                        )
                    }
                    eventChannel.send(
                        FavoriteEvent.Success(UiText.StringResource(R.string.success_delete_favorite))
                    )
                }
            }
        }
    }
}