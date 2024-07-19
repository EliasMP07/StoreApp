package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.remote.FakeStoreApi
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getSingleProduct(idProduct: String): Response<Product> {
        return try {
            val result = api.getSingleProduct(idProduct).toProduct()
            Response.Success(result)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Response.Failure(Exception("No Internet Connection"))
        } catch (e: IOException) {
            e.printStackTrace()
            Response.Failure(Exception("Network Error"))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
}

