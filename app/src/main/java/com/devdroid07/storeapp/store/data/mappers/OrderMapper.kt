package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.store.OrderDto
import com.devdroid07.storeapp.store.domain.model.Order

fun OrderDto.toOrder(): Order{
    return Order(
        id = id,
        status = status,
        timestamp =  timestamp,
        products = products.map {
            it.toProduct()
        },
        address = address.toAddress(),
        transactionAmount = transactionAmount
    )
}