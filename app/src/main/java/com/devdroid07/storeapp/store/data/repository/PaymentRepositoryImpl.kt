package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.core.domain.util.asEmptyDataResult
import com.devdroid07.storeapp.store.data.network.api.PaymentApiService
import com.devdroid07.storeapp.store.data.network.dto.store.payment.OrderRequest
import com.devdroid07.storeapp.store.data.network.dto.store.payment.Payer
import com.devdroid07.storeapp.store.data.network.dto.store.payment.PaymentRequest
import com.devdroid07.storeapp.store.domain.repository.PaymentRepository

class PaymentRepositoryImpl (
    private val sessionStorage: SessionStorage,
    private val paymentApiService: PaymentApiService
): PaymentRepository {
    override suspend fun createPayment(
        addressId: String,
        token: String,
        transactionAmount: Double
    ): EmptyResult<DataError.Network> {
        val result = safeCall {
            paymentApiService.paymentCreateAndOrder(
                PaymentRequest(
                    order = OrderRequest(
                        clientId = sessionStorage.get()?.id.orEmpty(),
                        transactionAmount = transactionAmount,
                        addressId = addressId,
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