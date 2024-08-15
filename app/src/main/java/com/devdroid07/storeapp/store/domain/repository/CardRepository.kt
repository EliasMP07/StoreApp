package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllMyCard(): Flow<Result<List<Card>, DataError.Network>>

    suspend fun createCard(
        cardNumber: String,
        expireDate: String,
        nameHeadline: String,
        cvv: String
    ): Result<Card, DataError.Network>

    suspend fun createCardToken(
        year: String,
        month: Int,
        securityCode: String,
        cardNumber: String,
        cardHolder: String
    ): Result<String, DataError.Network>
}