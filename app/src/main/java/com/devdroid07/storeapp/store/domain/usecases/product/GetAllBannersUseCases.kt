package com.devdroid07.storeapp.store.domain.usecases.product

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Banner
import com.devdroid07.storeapp.store.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetAllBannersUseCases (
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Result<List<Banner>, DataError.Network>>{
        return repository.getAllBanner()
    }
}