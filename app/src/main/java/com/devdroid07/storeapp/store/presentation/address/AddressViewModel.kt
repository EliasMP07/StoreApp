package com.devdroid07.storeapp.store.presentation.address

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.insert
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressUseCases: AddressUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(AddressState())
    val state: StateFlow<AddressState> get() = _state.asStateFlow()

    private val eventChannel = Channel<AddressEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _state.value.postalCode.textAsFlow().onEach { postalCode ->
            if (postalCode.length >= 5) {
                getInfoByPostalCode(postalCode.toString())
            } else {
                _state.update { addressState ->
                    addressState.state.clearText()
                    addressState.mayoralty.clearText()
                    addressState.settlement.clearText()
                    addressState.copy(
                        isCorrectPostalCode = false,
                        settlementList = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)

        combine(
            state.value.street.textAsFlow(),
            state.value.postalCode.textAsFlow(),
            state.value.mayoralty.textAsFlow()
        ) { postal, mayoralty, street ->
            _state.update { addressState ->
                addressState.copy(
                    canCreateAddress = postal.isNotEmpty() && mayoralty.isNotEmpty() && street.isNotEmpty()
                )
            }
        }.launchIn(viewModelScope)
        getAllMyAddress()
    }

    fun onAction(
        action: AddressAction,
    ) {
        when (action) {
            AddressAction.OnToggleDropdownMenuClick -> {
                _state.update { addressState ->
                    addressState.copy(
                        isExpandedDropdownMenu = !addressState.isExpandedDropdownMenu
                    )
                }
            }
            is AddressAction.OnSettlementChange -> {
                _state.value.settlement.edit {
                    replace(
                        0,
                        this.length,
                        action.settlement
                    )
                }
            }
            AddressAction.OnCreateAddress -> createAddress()
            is AddressAction.OnDeleteAddress -> deleleAddress(addressId = action.addressId)
            AddressAction.OnRetryClick -> getAllMyAddress()
            else -> Unit
        }
    }

    private fun createAddress() {
        viewModelScope.launch {
            _state.update { addressState ->
                addressState.copy(
                    isCreatingAddress = true
                )
            }
            val result = addressUseCases.createAddressUseCase(
                postalCode = state.value.postalCode.text.toString().trim(),
                settlement = state.value.settlement.text.toString(),
                mayoralty = state.value.mayoralty.text.toString(),
                phoneNumber = state.value.numberContact.text.toString(),
                reference = state.value.references.text.toString(),
                state = state.value.state.text.toString(),
                street = state.value.street.text.toString()
            )
            _state.update { addressState ->
                addressState.copy(
                    isCreatingAddress = false
                )
            }
            _state.update { addressState ->
                when (result) {
                    is Result.Error -> {
                        eventChannel.send(
                            AddressEvent.Error(UiText.StringResource(R.string.error_create_address))
                        )
                        addressState.copy(
                            addressList = emptyList()
                        )
                    }
                    is Result.Success -> {
                        val list = state.value.addressList.toMutableList()
                        list.add(result.data)
                        eventChannel.send(
                            AddressEvent.Success(UiText.StringResource(R.string.success_create_address))
                        )
                        addressState.apply {
                            state.clearText()
                            postalCode.clearText()
                            settlement.clearText()
                            mayoralty.clearText()
                            numberContact.clearText()
                            street.clearText()
                            references.clearText()
                        }
                        addressState.copy(
                            addressList = list
                        )
                    }
                }
            }
        }
    }

    private fun getAllMyAddress() {
        viewModelScope.launch {
            _state.update {addressState ->
                addressState.copy(
                    isLoading = true,
                    error = null
                )
            }
            val result = addressUseCases.getAllMyAddressUseCase()
            _state.update { addressState ->
                when (result) {
                    is Result.Error -> {
                        addressState.copy(
                            error = result.error.asUiText(),
                            isLoading = false,
                            addressList = emptyList()
                        )
                    }
                    is Result.Success -> {
                        addressState.copy(
                            isLoading = false,
                            error = null,
                            addressList = result.data
                        )
                    }
                }
            }
        }
    }

    private fun deleleAddress(addressId: Int) {
        viewModelScope.launch {
            val result = addressUseCases.deleteAddressUseCase(addressId)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        AddressEvent.Error(UiText.StringResource(R.string.error_delete_address))
                    )
                }
                is Result.Success -> {
                    _state.update { addressState ->
                        val newList = addressState.addressList.toMutableList()
                        newList.removeIf {
                            it.id == addressId
                        }
                        addressState.copy(
                            addressList = newList
                        )
                    }
                    eventChannel.send(
                        AddressEvent.Success(UiText.StringResource(R.string.success_delete_address))
                    )
                }
            }
        }
    }

    private fun getInfoByPostalCode(infoCode: String) {
        viewModelScope.launch {
            addressUseCases.getInfoByPostalCodeUseCase(infoCode).collect { result ->
                _state.update { addressState ->
                    when (result) {
                        is Result.Error -> {
                            addressState.copy(
                                isCorrectPostalCode = false
                            )
                        }
                        is Result.Success -> {
                            val settlements: List<String> = result.data.map {
                                it.asentamiento
                            }
                            addressState.state.edit {
                                insert(
                                    0,
                                    result.data.first().estado
                                )
                            }
                            addressState.mayoralty.edit {
                                insert(
                                    0,
                                    result.data.first().estado
                                )
                            }
                            addressState.copy(
                                isCorrectPostalCode = true,
                                settlementList = settlements
                            )
                        }
                    }
                }

            }
        }
    }

}