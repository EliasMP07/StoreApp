package com.devdroid07.storeapp.store.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.usecases.product.ProductUseCases
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
class HomeViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> get() = _state.asStateFlow()

    init {
        getUser()
        loadProducts()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.RetryClick -> {
                loadProducts()
            }
            else -> Unit
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    user = sessionStorage.get()
                )
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }
            combine(productUseCases.getAllProducts(), productUseCases.getAllBannersUseCases()){products, banners ->
                products to banners
            }.collectLatest { (products, banners) ->
                when{
                    products is Result.Success && banners is Result.Success -> {
                        _state.update {
                            it.copy(
                                error = null,
                                bannersList = banners.data,
                                productsList = products.data,
                                productRecommended = products.data.firstOrNull { product ->
                                    product.ratingRate > 4.0
                                } ?: Product(),
                                isLoading = false
                            )
                        }
                    }
                    products is Result.Error && banners is Result.Error -> {
                        _state.update {
                            it.copy(
                                error = products.error.asUiText(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

}
