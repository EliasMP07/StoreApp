package com.devdroid07.storeapp.store.presentation.myCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.SnackBarStyle
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.model.Cart
import com.devdroid07.storeapp.store.domain.usecases.cart.CartUseCases
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
    private val cartUseCase: CartUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(MyCartState())
    val state: StateFlow<MyCartState> get() = _state.asStateFlow()

    private val eventChannel = Channel<MyCartEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getMyCart()
    }

    fun onAction(
        action: MyCartAction,
    ) {
        when (action) {
            is MyCartAction.OnRemoveProduct -> onRemoveProductFromCart(action.idProduct)
            MyCartAction.OnRetryClick -> getMyCart()
            MyCartAction.OnRestoreRemove -> onUndoProductRemoval()
            MyCartAction.OnConfirmRemove -> onConfirmProductRemoval()
            MyCartAction.OnUpdateQuantityClick -> onUpdateQuantity()
            is MyCartAction.OnChangeQuantityAndProductId -> {
                _state.update {
                    it.copy(
                        quantity = action.quantity,
                        productId = action.productId
                    )
                }
            }
            MyCartAction.DownClickSelectedQuantity -> {
                _state.update {
                    var cout = it.quantity
                    if (it.quantity >= 1) {
                        cout--
                    }
                    it.copy(quantity = cout)
                }
            }
            MyCartAction.UpClickSelectedQuantity -> {
                _state.update {
                    var cout = it.quantity
                    cout++
                    it.copy(
                        quantity = cout
                    )
                }
            }
            else -> Unit
        }
    }

    private fun getMyCart() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }
            cartUseCase.getMyCartUseCase().collectLatest { result ->
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
                                totalSale = result.data.sumOf { it.price.toDouble() * it.quantity },
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

    //Funcion que actualiza la cantidad de los productos
    private fun onUpdateQuantity() {
        viewModelScope.launch {
            _state.update { it.copy(isUpdatingQuantity = true) }
            val result = cartUseCase.updateQuantityProductUseCase(
                productId = _state.value.productId,
                quantity = _state.value.quantity
            )
            _state.update {
                it.copy(
                    isUpdatingQuantity = false,
                    labelButton = null
                )
            }
            when (result) {
                is Result.Error -> {
                    eventChannel.send(MyCartEvent.Error(error = result.error.asUiText()))
                }
                is Result.Success -> {
                    getMyCart()
                    eventChannel.send(
                        MyCartEvent.Success(
                            UiText.StringResource(R.string.success_update_quantity),
                            SnackBarStyle.SuccessUpdateQuantity
                        )
                    )
                }
            }
        }
    }

    //Funcion que ya elimina del backend el producto del carrito
    private fun onConfirmProductRemoval() {
        viewModelScope.launch {
            val restoredItem = _state.value.removedItems.lastOrNull()
            restoredItem?.let {
                val result = cartUseCase.removeProductMyCartUseCase(it.idProduct.toInt())
                if (result is Result.Error) {
                    //Si sucede un error a la hora de eliminar el producto del backen lo vuelve agregar a la lista
                    _state.update { currentState ->
                        currentState.copy(
                            myCart = currentState.myCart + restoredItem,
                            removedItems = currentState.removedItems - restoredItem
                        )
                    }
                    eventChannel.send(MyCartEvent.Error(error = result.error.asUiText()))
                } else {
                    _state.update { currentState ->
                        currentState.copy(removedItems = currentState.removedItems - restoredItem)
                    }
                }
            }
        }
    }

    //Funcion que elimina el producto de la lista pero no del backend
    private fun onRemoveProductFromCart(idProduct: Int) {
        viewModelScope.launch {
            val newCart = _state.value.myCart.toMutableList()
            val removedItem = newCart.find { it.idProduct == idProduct.toString() }
            removedItem?.let { cart ->
                newCart.remove(cart)
                _state.update {
                    it.copy(
                        myCart = newCart,
                        removedItems = it.removedItems + cart, //Se guarda el producto en una lista aparte por si se quiere volver agregar
                        labelButton = UiText.StringResource(R.string.label_undo)
                    )
                }
                eventChannel.send(
                    MyCartEvent.Success(
                        UiText.StringResource(R.string.success_remove_cart),
                        SnackBarStyle.SuccessRemoveCart
                    )
                )
            }
        }
    }

    //Funcion que vuelve agregar el producto en la lista
    private fun onUndoProductRemoval() {
        val restoredItem = _state.value.removedItems.lastOrNull()
        restoredItem?.let {
            _state.update { currentState ->
                currentState.copy(
                    myCart = currentState.myCart + restoredItem,
                    removedItems = currentState.removedItems - restoredItem
                )
            }
        }
    }

}