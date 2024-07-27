package com.devdroid07.storeapp.store.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val storeUseCases: StoreUseCases
): ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> get() = _state.asStateFlow()

    fun onAction(action: SearchAction){
        when(action){
            is SearchAction.OnChangeQuery -> {
                _state.update {currentState ->
                    currentState.copy(
                        query = action.query
                    )
                }
            }
            SearchAction.OnSearch -> searchProduct()
            else -> Unit
        }
    }

    private fun searchProduct(){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    isSearching = true,
                    error = null
                )
            }
            val result = storeUseCases.searchProductUseCase(query = _state.value.query)
            _state.update {currentState ->
                when(result){
                    is Result.Error -> {
                        currentState.copy(
                            isSearching = false,
                            products = emptyList(),
                            error = result.error.asUiText()
                        )
                    }
                    is Result.Success -> {
                        currentState.copy(
                            isSearching = false,
                            isEmpty = result.data.isEmpty(),
                            products = result.data,
                            error = null
                        )
                    }
                }
            }
        }
    }

}