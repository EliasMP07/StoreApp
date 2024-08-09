package com.devdroid07.storeapp.store.data.network.api

import com.devdroid07.storeapp.core.data.network.StoreResponse
import com.devdroid07.storeapp.store.data.network.dto.store.AddressDto
import com.devdroid07.storeapp.store.data.network.dto.store.AddressRequest
import com.devdroid07.storeapp.store.data.network.dto.store.AddressUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AddressApiService {

    @GET("address/getAllAddress/{id_user}")
    suspend fun getAllMyAddress(@Path("id_user") userId: String): Response<StoreResponse<List<AddressDto>>>

    @POST("address/create")
    suspend fun createAddress(@Body addressRequest: AddressRequest): Response<StoreResponse<AddressDto>>

    @DELETE("address/delete")
    suspend fun deleteAddress(@Query("address_id") addressId: String): Response<StoreResponse<String>>

    @PUT("/v1/address/update")
    suspend fun updateAddress(@Body addressUpdateRequest: AddressUpdateRequest): Response<StoreResponse<String>>

    @GET("address/getAddress/{id_address}")
    suspend fun getAddress(@Path("id_address") addressId: String): Response<StoreResponse<AddressDto>>

}