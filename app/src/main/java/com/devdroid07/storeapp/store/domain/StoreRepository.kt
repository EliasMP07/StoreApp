package com.devdroid07.storeapp.store.domain

import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

   fun getAllProduct(): Flow<Response<List<Product>>>

}