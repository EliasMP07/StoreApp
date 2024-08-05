package com.devdroid07.storeapp.store.domain.usecases.card

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Card
import com.devdroid07.storeapp.store.domain.repository.CardRepository

class CreateCardUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(
        cardNumber: String,
        expireDate: String,
        nameHeadline: String,
        cvv: String,
    ): Result<Card, DataError.Network>{
        return repository.createCard(cardNumber, expireDate, nameHeadline, cvv)
    }
}