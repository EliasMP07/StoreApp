package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.data.mappers.toCard
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.remote.dto.store.CardRequest
import com.devdroid07.storeapp.store.domain.model.Card
import com.devdroid07.storeapp.store.domain.repository.CardRepository

class CardRepositoryImpl (
    private val sessionStorage: SessionStorage,
    private val storeApiService: StoreApiService
): CardRepository {

    override suspend fun getAllMyCard(): Result<List<Card>, DataError.Network> {
        val result = safeCall2 {
            storeApiService.getAllMyCards(
                sessionStorage.get()?.id.orEmpty()
            )
        }
        return when(result){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(
                    result.data.data?.map {
                        it.toCard()
                    }?: emptyList()
                )
            }
        }
    }

    override suspend fun createCard(
        cardNumber: String,
        expireDate: String,
        nameHeadline: String,
        cvv: String,
    ): Result<Card, DataError.Network> {
        val result = safeCall2 {
            storeApiService.createCard(
                CardRequest(
                    userId = sessionStorage.get()?.id.orEmpty(),
                    cardNumber = cardNumber,
                    expireDate = expireDate,
                    nameHeadline = nameHeadline,
                    cvv = cvv
                )
            )
        }

        return when(result){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data?.toCard()?:Card())
            }
        }
    }

}