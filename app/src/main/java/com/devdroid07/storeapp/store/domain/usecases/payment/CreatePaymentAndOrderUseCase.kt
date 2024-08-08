package com.devdroid07.storeapp.store.domain.usecases.payment

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult
import com.devdroid07.storeapp.store.domain.repository.PaymentRepository

class CreatePaymentAndOrderUseCase(
    private val repository: PaymentRepository,
) {
    suspend operator fun invoke(
        addressId: String,
        token: String,
        transactionAmount: Double
    ): EmptyResult<DataError.Network> {
        return repository.createPayment(
            addressId = addressId,
            token = token,
            transactionAmount = transactionAmount
        )
    }
}