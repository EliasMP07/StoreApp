package com.devdroid07.storeapp.store.domain.usecases.card

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.repository.CardRepository

class CreateCardTokenUseCase (
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(
        year: String,
        month: Int,
        securityCode: String,
        cardNumber: String,
        cardHolder: String,
    ): Result<String, DataError.Network> {
        return cardRepository.createCardToken(
            cardHolder = cardHolder,
            securityCode = securityCode,
            cardNumber = cardNumber,
            month = month,
            year = year
        )
    }
}