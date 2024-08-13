package com.devdroid07.storeapp.store.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.usecases.order.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases
): ViewModel(){

    private val _state = MutableStateFlow(OrdersState())
    val state: StateFlow<OrdersState> get() = _state.asStateFlow()

    init {
        getAllOrders()
    }

    fun onAction(action: OrdersAction){
        when(action){
            OrdersAction.OnRetry -> getAllOrders()
            else -> Unit
        }
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            orderUseCases.getAllOrderUseCase().collectLatest {result ->
                _state.update {
                    when(result){
                        is Result.Error -> it.copy(
                            isLoading = false,
                            error = result.error.asUiText()
                        )
                        is Result.Success -> it.copy(
                            isLoading = false,
                            error = null,
                            ordersList = result.data
                        )
                    }
                }
            }
        }
    }

}