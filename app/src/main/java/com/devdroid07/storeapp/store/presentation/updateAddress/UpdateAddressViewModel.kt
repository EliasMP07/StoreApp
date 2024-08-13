package com.devdroid07.storeapp.store.presentation.updateAddress

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.insert
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class UpdateAddressViewModel @Inject constructor(
    private val addressUseCase: AddressUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _state = MutableStateFlow(UpdateAddressState())
    val state: StateFlow<UpdateAddressState> get() = _state.asStateFlow()

    private val eventChannel = Channel<UpdateAddressEvent>()
    val events = eventChannel.receiveAsFlow()

    private val addressId: String = checkNotNull(savedStateHandle[NavArgs.AddressID.key])


    init {
        getAddress()
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
    }

    fun onAction(
        action: UpdateAddressAction,
    ) {
        when (action) {
            UpdateAddressAction.OnUpdateAddress -> updateAddress()
            UpdateAddressAction.OnToggleDropdownMenuClick -> {
                _state.update { it.copy(isExpandedDropdownMenu = !it.isExpandedDropdownMenu) }
            }
            is UpdateAddressAction.OnSettlementChange -> {
                _state.value.settlement.edit {
                    replace(
                        0,
                        this.length,
                        action.settlement
                    )
                }
            }
            else -> Unit
        }
    }

    private fun updateAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isUpdatingAddress = true) }
            val result = addressUseCase.updateAddressUseCase(
                id = addressId,
                postalCode = state.value.postalCode.text.toString().trim(),
                settlement = state.value.settlement.text.toString(),
                mayoralty = state.value.mayoralty.text.toString(),
                phoneNumber = state.value.numberContact.text.toString(),
                reference = state.value.references.text.toString(),
                state = state.value.state.text.toString(),
                street = state.value.street.text.toString()
            )
            _state.update { it.copy(isUpdatingAddress = false) }
            when (result) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.BAD_REQUEST -> {
                            eventChannel.send(UpdateAddressEvent.Error(UiText.StringResource(R.string.fields_empty)))
                        }
                        else -> {
                            eventChannel.send(UpdateAddressEvent.Error(result.error.asUiText()))
                        }
                    }

                }
                is Result.Success -> {
                    eventChannel.send(UpdateAddressEvent.Success)
                }
            }
        }
    }

    private fun getAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = addressUseCase.getSingleAddressUseCase(addressId)
            _state.update {
                when (result) {
                    is Result.Error -> {
                        it.copy(
                            isLoading = false,
                            error = result.error.asUiText()
                        )
                    }
                    is Result.Success -> {
                        it.copy(
                            state = it.state.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.state
                                    )
                                }
                            },
                            references = it.references.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.reference
                                    )
                                }
                            },
                            street = it.street.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.street
                                    )
                                }
                            },
                            settlement = it.settlement.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.settlement
                                    )
                                }
                            },
                            mayoralty = it.mayoralty.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.mayoralty
                                    )
                                }
                            },
                            numberContact = it.numberContact.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.phoneNumber
                                    )
                                }
                            },
                            postalCode = it.postalCode.apply {
                                edit {
                                    insert(
                                        0,
                                        result.data.postalCode
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


    private fun getInfoByPostalCode(infoCode: String) {
        viewModelScope.launch {
            addressUseCase.getInfoByPostalCodeUseCase(infoCode).collect { result ->
                _state.update {
                    when (result) {
                        is Result.Error -> {
                            it.copy(isCorrectPostalCode = false)
                        }
                        is Result.Success -> {
                            val settlements: List<String> = result.data.map { postalCode ->
                                postalCode.asentamiento
                            }
                            it.copy(
                                state = it.state.apply {
                                    edit {
                                        insert(
                                            0,
                                            result.data.first().estado
                                        )
                                    }
                                },
                                mayoralty = it.mayoralty.apply {
                                    edit {
                                        insert(
                                            0,
                                            result.data.first().municipio
                                        )
                                    }
                                },
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
