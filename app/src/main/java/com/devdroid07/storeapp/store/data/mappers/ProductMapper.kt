package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.store.ProductDto
import com.devdroid07.storeapp.store.domain.model.Product

fun ProductDto.toProduct(): Product{
    return Product(
        id = id,
        title = title,
        price = price.toDouble(),
        description = description,
        category = category,
        image = image,
        isFavorite = isFavorite,
        ratingCount = rating.count,
        ratingRate = rating.rate
    )
}