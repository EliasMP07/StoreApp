package com.devdroid07.storeapp.store.domain.usecases

import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response

class GetSingleProduct(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(idProduct: String): Response<Product>{
        return repository.getSingleProduct(idProduct)
    }
}