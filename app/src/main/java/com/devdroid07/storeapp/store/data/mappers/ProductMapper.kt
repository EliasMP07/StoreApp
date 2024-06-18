package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.remote.dto.ProductDto
import com.devdroid07.storeapp.store.domain.model.Product

fun ProductDto.toProduct(): Product{
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        ratingCount = rating.count,
        ratingRate = rating.rate
    )
}