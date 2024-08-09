package com.devdroid07.storeapp.store.data.repository

import com.devdroid07.storeapp.core.data.network.safeCall2
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import com.devdroid07.storeapp.store.data.mappers.toCard
import com.devdroid07.storeapp.store.data.remote.api.MercadoPagoApiService
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.remote.dto.mercadoPago.Cardholder
import com.devdroid07.storeapp.store.data.remote.dto.mercadoPago.MercadoPagoCardTokenRequest
import com.devdroid07.storeapp.store.data.remote.dto.store.CardRequest
import com.devdroid07.storeapp.store.domain.model.Card
import com.devdroid07.storeapp.store.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val mercadoPagoApiService: MercadoPagoApiService,
    private val storeApiService: StoreApiService,
) : CardRepository {

    override suspend fun getAllMyCard(): Flow<Result<List<Card>, DataError.Network>> = flow {
        val result = safeCall2 {
            storeApiService.getAllMyCards(
                sessionStorage.get()?.id.orEmpty()
            )
        }
        when (result) {
            is Result.Error -> {
                emit(Result.Error(result.error))
            }
            is Result.Success -> {
                emit(
                    Result.Success(
                        result.data.data?.map {
                            it.toCard()
                        } ?: emptyList()
                    )
                )
            }
        }
    }

    override suspend fun createCard(
        cardNumber: String,
        expireDate: String,
        nameHeadline: String,
        cvv: String
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

        return when (result) {
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.data?.toCard() ?: Card())
            }
        }
    }

    override suspend fun createCardToken(
        year: String,
        month: Int,
        securityCode: String,
        cardNumber: String,
        cardHolder: String,
    ): Result<String, DataError.Network> {
        val result = safeCall2 {
            mercadoPagoApiService.createCardToken(
                MercadoPagoCardTokenRequest(
                    securityCode = securityCode,
                    expirationYear = year,
                    expirationMonth = month,
                    cardNumber = cardNumber,
                    cardHolder = Cardholder(
                        name = cardHolder
                    )
                )
            )
        }
        return when(result){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.id)
            }
        }
    }

}