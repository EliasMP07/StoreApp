package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.remote.dto.CartDto
import com.devdroid07.storeapp.store.domain.model.Cart

fun CartDto.toCart(): Cart{
    return Cart(
        idProduct = idProduct,
        title = title,
        description = description,
        price = price,
        image = image,
        rating = rating,
        category = category,
        quantity = quantity
    )
}