package com.devdroid07.storeapp.store.presentation.address

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.insert
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class AddressViewModel @Inject constructor(
    private val storeRepository: StoreRepository
): ViewModel() {

    private val _state = MutableStateFlow(AddressState())
    val state: StateFlow<AddressState> get() = _state.asStateFlow()

    init {
        _state.value.postalCode.textAsFlow().onEach {postalCode ->
            if (postalCode.length >= 5){
                getInfoByPostalCode(postalCode.toString())
            }else{
                _state.update {addressState ->
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
        action: AddressAction
    ){
        when(action){
            AddressAction.OnToggleDropdownMenuClick -> {
                _state.update { addressState ->
                    addressState.copy(
                        isExpandedDropdownMenu = !addressState.isExpandedDropdownMenu
                    )
                }
            }
            is AddressAction.OnSettlementChange -> {
                _state.value.settlement.edit {
                    replace(0, this.length, action.settlement)
                }
            }
        }
    }

    private fun getInfoByPostalCode(infoCode: String){
        viewModelScope.launch {
            storeRepository.getInfoByPostalCode(infoCode).collect {result ->
                _state.update {addressState ->
                    when(result){
                        is Result.Error -> {
                            addressState.copy(
                                isCorrectPostalCode = false
                            )
                        }
                        is Result.Success -> {
                            val settlements: List<String> = result.data.map {
                                it.asentamiento
                            }
                            addressState.state.edit { insert(0, result.data.first().estado) }
                            addressState.mayoralty.edit { insert(0, result.data.first().estado) }
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