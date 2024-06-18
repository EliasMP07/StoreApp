package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.remote.FakeStoreApi
import com.devdroid07.storeapp.store.domain.StoreRepository
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.net.UnknownHostException

class StoreRepositoryImpl(
    private val api: FakeStoreApi
) : StoreRepository {
    override fun getAllProduct(): Flow<Response<List<Product>>> {
        return flow {
            emit(Response.Loading)
            try {
                val apiResponse = api.getAllProducts()
                val products = apiResponse.map { it.toProduct() }
                emit(Response.Success(products))
            } catch (e: UnknownHostException) {
                emit(Response.Failure(Exception("No Internet Connection")))
                e.printStackTrace()
            } catch (e: IOException) {
                emit(Response.Failure(Exception("Network Error")))
                e.printStackTrace()
            } catch (e: Exception) {
                emit(Response.Failure(e))
                e.printStackTrace()
            }
        }
    }
}