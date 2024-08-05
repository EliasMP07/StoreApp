package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.remote.dto.store.CardDto
import com.devdroid07.storeapp.store.domain.model.Card

fun CardDto.toCard(): Card{
    return Card(
        id = id,
        cardNumber = cardNumber,
        cvv = cvv,
        nameHeadline = nameHeadline,
        expireDate = expireDate
    )
}