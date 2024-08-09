@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.store.presentation.productDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.core.presentation.ui.UiText
import com.devdroid07.storeapp.core.presentation.ui.asUiText
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.store.domain.usecases.cart.CartUseCases
import com.devdroid07.storeapp.store.domain.usecases.favorite.FavoriteUseCases
import com.devdroid07.storeapp.store.domain.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class ProductDetailViewModel @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val favoriteUseCases: FavoriteUseCases,
    private val productUseCases: ProductUseCases,
    private val cartUseCases: CartUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> get() = _state.asStateFlow()


    private val eventChannel = Channel<ProductDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.update { productDetailState ->
                productDetailState.copy(
                    user = sessionStorage.get()
                )
            }
        }
        _state.value.comment.textAsFlow()
            .onEach { comment ->
                _state.update { currentState ->
                    currentState.copy(
                        canReview = comment.isNotEmpty() && !currentState.isEvaluating,
                    )
                }
            }
            .launchIn(viewModelScope)
        val productId = savedStateHandle[NavArgs.ProductID.key] ?: "1"
        getProduct(productId = productId)
    }

    private fun getReviews(productId: String) {
        viewModelScope.launch {
            _state.update { productDetailState ->
                productDetailState.copy(
                    errorReviews = null,
                    isLoadingReview = true
                )
            }
            productUseCases.getReviewsProductUseCase(productId).collectLatest { result ->
                _state.update { productDetailState ->
                    when (result) {
                        is Result.Error -> productDetailState.copy(
                            isLoadingReview = false,
                            errorReviews = result.error.asUiText(),
                            reviewsList = emptyList()
                        )
                        is Result.Success -> productDetailState.copy(
                            isLoadingReview = false,
                            errorReviews = null,
                            reviewsList = result.data
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            ProductDetailAction.OnReviewsClick -> {
                getReviews(state.value.product.id.toString())
            }
            ProductDetailAction.OnRetryReview -> {
                getReviews(state.value.product.id.toString())
            }
            ProductDetailAction.OnRetry -> {
                getProduct(state.value.product.id.toString())
            }
            ProductDetailAction.OnMoreInfoClick -> {
                _state.update { it.copy(isExpanded = !state.value.isExpanded) }
            }
            is ProductDetailAction.OnAddMyCart -> {
                addMyCart(
                    idProduct = action.idProduct,
                    state.value.quantity
                )
            }
            ProductDetailAction.OnAddProductClick -> {
                _state.update { currentState ->
                    var cout = currentState.quantity
                    cout++
                    currentState.copy(quantity = cout)
                }
            }
            ProductDetailAction.OnRemoveProductClick -> {
                _state.update { currentState ->
                    var cout = currentState.quantity
                    if (currentState.quantity >= 1) {
                        cout--
                    }
                    currentState.copy(quantity = cout)
                }
            }
            ProductDetailAction.OnReviewProductClick -> {
                addReviewProduct()
            }
            is ProductDetailAction.OnRantingChange -> {
                _state.update { productDetailState -> productDetailState.copy(ranting = action.rating) }
            }
            is ProductDetailAction.AddFavoriteClick -> {
                addFavorite(action.productId)
            }
            is ProductDetailAction.RemoveFavoriteClick -> {
                removeFavorite(action.productId)
            }
            else -> Unit
        }
    }

    private fun getProduct(productId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    error = null,
                    isLoading = true
                )
            }
            val result = productUseCases.getSingleProduct(
                productId
            )
            when (result) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            error = result.error.asUiText(),
                            isLoading = false
                        )
                    }
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            error = null,
                            product = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun addMyCart(
        idProduct: String,
        quantity: Int
    ) {
        viewModelScope.launch {

            val result = cartUseCases.addMyCartUseCase(
                idProduct,
                quantity
            )

            when (result) {
                is Result.Error -> {
                    eventChannel.send(ProductDetailEvent.Error(UiText.StringResource(R.string.error_add_cart)))
                }
                is Result.Success -> {
                    eventChannel.send(ProductDetailEvent.Success(UiText.StringResource(R.string.success_add_cart)))
                }
            }
        }
    }

    private fun addFavorite(idProduct: String) {
        viewModelScope.launch {
            val result = favoriteUseCases.addFavoriteProductUseCase(idProduct)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        ProductDetailEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    _state.update { currentState ->
                        val updatedProducts = currentState.product.copy(
                            isFavorite = true
                        )
                        currentState.copy(
                            product = updatedProducts
                        )
                    }
                    eventChannel.send(
                        ProductDetailEvent.Success(UiText.StringResource(R.string.success_add_favorite))
                    )
                }
            }
        }
    }

    private fun removeFavorite(idProduct: String) {
        viewModelScope.launch {

            when (val result = favoriteUseCases.removeFavoriteProductUseCase(idProduct)) {
                is Result.Error -> {
                    eventChannel.send(
                        ProductDetailEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    _state.update { currentState ->
                        val updatedProducts = currentState.product.copy(
                            isFavorite = false
                        )
                        currentState.copy(
                            product = updatedProducts
                        )
                    }
                    eventChannel.send(
                        ProductDetailEvent.Success(UiText.StringResource(R.string.success_delete_favorite))
                    )
                }
            }
        }
    }

    private fun addReviewProduct() {
        viewModelScope.launch {
            _state.update { productDetailState ->
                productDetailState.copy(
                    isEvaluating = true
                )
            }
            val result = productUseCases.addReviewProductUseCase(
                productId = state.value.product.id.toString(),
                rating = state.value.ranting,
                comment = state.value.comment.text.toString()
            )
            _state.update { productDetailState ->
                productDetailState.copy(
                    isEvaluating = false
                )
            }
            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        ProductDetailEvent.Error(result.error.asUiText())
                    )
                }
                is Result.Success -> {
                    eventChannel.send(
                        ProductDetailEvent.Success(UiText.StringResource(R.string.success_add_review))
                    )
                    _state.update { productDetailState ->
                        productDetailState.comment.clearText()
                        productDetailState.copy(
                            ranting = 0.0
                        )
                    }
                }
            }
        }
    }
}