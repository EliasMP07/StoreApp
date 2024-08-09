package com.devdroid07.storeapp.store.domain.usecases.product

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.repository.ProductRepository

class GetSingleProduct(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(idProduct: String): Result<Product, DataError.Network> {
        return repository.getSingleProduct(idProduct)
    }
}