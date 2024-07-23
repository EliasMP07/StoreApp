package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow

class GetAllProducts (
    private val repository: StoreRepository
) {
    suspend operator fun invoke(): Flow<Response<List<Product>>>{
        return repository.getAllProduct()
    }
}