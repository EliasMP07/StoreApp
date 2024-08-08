package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.EmptyResult

interface PaymentRepository {
    suspend fun createPayment(
        addressId: String,
        token: String,
        transactionAmount: Double
    ): EmptyResult<DataError.Network>
}