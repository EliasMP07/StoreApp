package com.devdroid07.storeapp.store.presentation.productDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel()
class ProductDetailViewModel @Inject constructor(

): ViewModel(){

    var state by mutableStateOf(ProductDetailState())
        private set

    fun onAction(action: ProductDetailAction){
        when(action){
            else -> Unit
        }
    }
}