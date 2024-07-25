package com.devdroid07.storeapp.store.presentation.productDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.navigation.util.NavArgs
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

@HiltViewModel()
class ProductDetailViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){

    private val _state = MutableStateFlow(ProductDetailState())
    val state:  StateFlow<ProductDetailState> get() = _state.asStateFlow()


    private val eventChannel = Channel<ProductDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val productId = savedStateHandle[NavArgs.ProductID.key] ?: "1"
        getProduct(productId = productId)
    }

    private fun getProduct(productId: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    error = null,
                    isLoading = true
                )
            }
            val result = storeUseCases.getSingleProduct(
                productId
            )
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
                            product = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun addMyCart(idProduct: String, quantity: Int){
        viewModelScope.launch {

            val result = storeUseCases.addMyCartUseCase(idProduct, quantity)

            when(result){
                is Result.Error -> {
                    eventChannel.send(ProductDetailEvent.Error(UiText.StringResource(R.string.error_add_cart)))
                }
                is Result.Success -> {
                    eventChannel.send(ProductDetailEvent.Success(UiText.StringResource(R.string.success_add_cart)))
                }
            }
        }
    }

    fun onAction(action: ProductDetailAction){
        when(action){
            ProductDetailAction.OnMoreInfoClick -> {
                _state.update {
                    it.copy(
                        isExpanded = !_state.value.isExpanded
                    )
                }
            }
            is ProductDetailAction.OnAddMyCart -> {
                addMyCart(idProduct = action.idProduct, _state.value.quantity)
            }
            ProductDetailAction.OnAddProductClick -> {
                _state.update {currentState ->
                    var cout = currentState.quantity
                    cout++
                    currentState.copy(
                        quantity = cout
                    )
                }
            }
            ProductDetailAction.OnRemoveProductClick -> {
                _state.update {currentState ->
                    var cout = currentState.quantity
                    if (currentState.quantity >= 1){
                        cout--
                    }
                    currentState.copy(
                        quantity = cout
                    )
                }
            }
            else -> Unit
        }
    }
}