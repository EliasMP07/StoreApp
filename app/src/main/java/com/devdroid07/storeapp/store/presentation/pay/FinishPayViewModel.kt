package com.devdroid07.storeapp.store.presentation.pay

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import com.devdroid07.storeapp.store.domain.usecases.card.CardUseCases
import com.devdroid07.storeapp.store.domain.usecases.payment.PaymentUseCases
import com.devdroid07.storeapp.store.domain.usecases.product.ProductUseCases
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishPayViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val addressUseCases: AddressUseCases,
    private val cardUseCases: CardUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val paymentUseCases: PaymentUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(FinishPayState())
    val state: StateFlow<FinishPayState> get() = _state.asStateFlow()

    init {
        val cardId = savedStateHandle[NavArgs.CardID.key] ?: "1"
        val addressId = savedStateHandle[NavArgs.AddressID.key] ?: "1"
        val token = savedStateHandle[NavArgs.TokenId.key] ?: "1"
        getMyCard(
            addressId,
            cardId,
            token
        )
    }

    fun onAction(
        action: FinishPayAction,
    ) {
        when (action) {
            FinishPayAction.OnPayClick -> pay()
            else -> Unit
        }
    }

    private fun pay() {
        viewModelScope.launch {
            _state.update { it.copy(isPaying = true) }
            val result = paymentUseCases.createPaymentAndOrderUseCase(
                addressId = state.value.address.id.toString(),
                token = state.value.token,
                transactionAmount = state.value.totalPrice
            )
            _state.update {
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            DataError.Network.NO_INTERNET -> {
                                it.copy(
                                    isPaying = false,
                                    error = result.error.asUiText()
                                )
                            }
                            else -> {
                                it.copy(
                                    isPaying = false,
                                    pay = ProgressPay.FAILURE
                                )
                            }
                        }

                    }
                    is Result.Success -> {
                        it.copy(
                            isPaying = false,
                            pay = ProgressPay.SUCCESS,
                        )
                    }
                }
            }
        }
    }

    private fun getMyCard(
        addressId: String,
        cardId: String,
        token: String,
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            combine(
                addressUseCases.getAllMyAddressUseCase(),
                productUseCases.getMyCartUseCase(),
                cardUseCases.getAllMyCardsUseCase()
            ) { addressResult, cartResult, cardResult ->
                Triple(
                    addressResult,
                    cartResult,
                    cardResult
                )
            }.collectLatest { (addressResult, cartResult, cardResult) ->
                when {
                    addressResult is Result.Error && cartResult is Result.Error && cardResult is Result.Error -> {
                        _state.update { finishPayState ->
                            finishPayState.copy(
                                isLoading = false,
                                error = addressResult.error.asUiText()
                            )
                        }
                    }
                    addressResult is Result.Success && cartResult is Result.Success && cardResult is Result.Success -> {
                        _state.update { finishPayState ->
                            finishPayState.copy(
                                token = token,
                                listCart = cartResult.data,
                                isLoading = false,
                                card = cardResult.data.first {
                                    it.id == cardId.toInt()
                                },
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