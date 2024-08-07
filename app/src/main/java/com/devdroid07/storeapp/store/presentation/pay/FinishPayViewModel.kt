package com.devdroid07.storeapp.store.presentation.pay

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.store.domain.model.Address
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import com.devdroid07.storeapp.store.domain.usecases.card.CardUseCases
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay
import com.devdroid07.storeapp.store.presentation.payment.PaymentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishPayViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases,
    private val addressUseCases: AddressUseCases,
    private val cardUseCases: CardUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(FinishPayState())
    val state: StateFlow<FinishPayState> get() = _state.asStateFlow()

    private val eventChannel = Channel<FinishPayEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val cardId = savedStateHandle[NavArgs.CardID.key] ?: "1"
        val addressId = savedStateHandle[NavArgs.AddressID.key] ?: "1"
        getMyCard(
            addressId,
            cardId
        )
    }

    fun onAction(
        action: FinishPayAction,
    ) {
        when (action) {
            FinishPayAction.OnPayClick -> simulatePay()
            else -> Unit
        }
    }

    private fun simulatePay() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isPaying = true
                )
            }
            delay(5000)
            _state.update {
                it.copy(
                    pay = ProgressPay.SUCCESS
                )
            }
            delay(1000)
            _state.update {
                it.copy(
                    isPaying = false
                )
            }
            delay(500)
            eventChannel.send(
                FinishPayEvent.Success
            )
        }
    }

    private fun getMyCard(
        addressId: String,
        cardId: String,
    ) {
        viewModelScope.launch {
            _state.update { finishPayState ->
                finishPayState.copy(
                    isLoading = true,
                    error = null
                )
            }
            combine(
                addressUseCases.getAllMyAddressUseCase(),
                storeUseCases.getMyCartUseCase(),
                cardUseCases.getAllMyCardsUseCase()
            ) { addressResult, cartResult, cardResult ->
                Triple(
                    addressResult,
                    cartResult,
                    cardResult
                )
            }.collectLatest { (addressResult, cartResult, cardResult) ->
                when {
                    addressResult is Result.Error || cartResult is Result.Error || cardResult is Result.Error -> {
                        val error = when{
                            addressResult is Result.Error -> addressResult.error
                            cardResult is Result.Error -> cardResult.error
                            cartResult is Result.Error -> cartResult.error
                            else -> null
                        }
                        _state.update { finishPayState ->
                            finishPayState.copy(
                                isLoading = false,
                                error = error?.asUiText()
                            )
                        }
                    }
                    addressResult is Result.Success && cartResult is Result.Success && cardResult is Result.Success -> {
                        _state.update { finishPayState ->
                            finishPayState.copy(
                                card = cardResult.data.first {
                                    it.id == cardId.toInt()
                                },
                                isLoading = false,
                                address = addressResult.data.first {
                                    it.id == addressId.toInt()
                                },
                                totalPrice = cartResult.data.sumOf {
                                    it.quantity.toDouble() * it.price.toDouble()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}