package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.store.cart.CartDto
import com.devdroid07.storeapp.store.domain.model.Cart

fun CartDto.toCart(): Cart{
    return Cart(
        idProduct = idProduct,
        title = title,
        price = price,
        image = image,
        category = category,
        quantity = quantity
    )
}