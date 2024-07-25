package com.devdroid07.storeapp.store.presentation.myCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailEvent
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
class MyCartViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MyCartState())
    val state: StateFlow<MyCartState> get() = _state.asStateFlow()

    private val eventChannel = Channel<MyCartEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getMyCart()
    }

    fun onAction(
        action: MyCartAction
    ) {
        when(action){
            is MyCartAction.OnRemoveProduct -> onRemoveProduct(action.idProduct)
            MyCartAction.OnRetryClick -> getMyCart()
            else -> Unit
        }
    }

    private fun onRemoveProduct(idProduct: Int) {
        viewModelScope.launch {
            val result = storeUseCases.removeProductMyCartUseCase(idProduct)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(MyCartEvent.Error(UiText.StringResource(R.string.error_remove_cart)))
                }
                is Result.Success -> {
                    val newCart = _state.value.myCart.toMutableList()
                    newCart.removeIf{
                        it.idProduct == idProduct.toString()
                    }
                    _state.update {currentState ->
                        currentState.copy(
                            myCart = newCart
                        )
                    }
                    eventChannel.send(MyCartEvent.Success(UiText.StringResource(R.string.success_remove_cart)))
                }
            }
        }
    }

    private fun getMyCart() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            storeUseCases.getMyCartUseCase().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = result.error.name
                            )
                        }
                    }
                    is Result.Success -> {
                        _state.update { currentState ->
                            currentState.copy(
                                myCart = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }
}