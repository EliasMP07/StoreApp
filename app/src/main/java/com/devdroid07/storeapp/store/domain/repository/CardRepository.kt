package com.devdroid07.storeapp.store.domain.repository

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Card

interface CardRepository {
    suspend fun getAllMyCard(): Result<List<Card>, DataError.Network>
    suspend fun createCard(
        cardNumber: String,
        expireDate: String,
        nameHeadline: String,
        cvv: String,
    ): Result<Card, DataError.Network>
}