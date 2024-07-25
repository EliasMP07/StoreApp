package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.mappers.toProduct
import com.devdroid07.storeapp.store.data.remote.StoreApiService
import com.devdroid07.storeapp.store.domain.model.Product
import com.devdroid07.storeapp.store.domain.model.Response
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.UnknownHostException

class StoreRepositoryImpl(
    private val api: StoreApiService,
    private val sessionStorage: SessionStorage
) : StoreRepository {
    override fun getAllProduct(): Flow<Response<List<Product>>> {
        return flow {
            emit(Response.Loading)
            try {
                val apiResponse = api.getAllProducts(token = sessionStorage.get()?.token.orEmpty())
                val products = apiResponse.data?.map { it.toProduct() }
                emit(Response.Success(products ?: emptyList()))
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
            val result = api.getSingleProduct(
                token = sessionStorage.get()?.token.orEmpty(),
                idProduct = idProduct
            ).data?.toProduct() ?: Product()
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

