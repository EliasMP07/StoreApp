package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.remote.dto.store.OrderRequest
import com.devdroid07.storeapp.store.data.remote.dto.store.Payer
import com.devdroid07.storeapp.store.data.remote.dto.store.PaymentRequest
import com.devdroid07.storeapp.store.domain.repository.PaymentRepository

class PaymentRepositoryImpl (
    private val sessionStorage: SessionStorage,
    private val storeApiService: StoreApiService
): PaymentRepository {
    override suspend fun createPayment(
        addressId: String,
        token: String,
        transactionAmount: Double
    ): EmptyResult<DataError.Network> {
        val result = safeCall2 {
            storeApiService.paymentCreateAndOrder(
                PaymentRequest(
                    order = OrderRequest(
                        clientId = sessionStorage.get()?.id.orEmpty(),
                        addressId = addressId
                    ),
                    token = token,
                    description = "Pagoo",
                    payer = Payer(
                        email = sessionStorage.get()?.email.orEmpty()
                    ),
                    transactionAmount = transactionAmount
                )
            )
        }

        return result.asEmptyDataResult()
    }
}