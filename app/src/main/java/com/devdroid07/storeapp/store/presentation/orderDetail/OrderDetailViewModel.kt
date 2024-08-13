package com.devdroid07.storeapp.store.presentation.orderDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.store.domain.usecases.order.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = MutableStateFlow(OrderDetailState())
    val state: StateFlow<OrderDetailState> get() = _state.asStateFlow()


    private val orderId: String = checkNotNull( savedStateHandle[NavArgs.OrderId.key])

    init {
        getOrder()
    }

    fun onAction(action: OrderDetailAction){
        when(action){
            OrderDetailAction.OnRetry -> getOrder()
            else -> Unit
        }
    }

    private fun getOrder() {
        viewModelScope.launch {
            _state.update { it.copy(error = null, isLoading = true) }
            val result = orderUseCases.getSingleOrderUseCase(orderId)
            _state.update {
                when(result){
                    is Result.Error -> it.copy(
                        isLoading = false,
                        error = result.error.asUiText()
                    )
                    is Result.Success -> it.copy(
                        order = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}