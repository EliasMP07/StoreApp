package com.devdroid07.storeapp.store.domain.usecases.card

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.domain.model.Card
import com.devdroid07.storeapp.store.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetAllMyCardsUseCase(
    private val repository: CardRepository,
) {
    suspend operator fun invoke(): Flow<Result<List<Card>, DataError.Network>> {

        return repository.getAllMyCard()

    }
}
