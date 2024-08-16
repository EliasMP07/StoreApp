package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.store.product.ReviewDto
import com.devdroid07.storeapp.store.domain.model.Review

fun ReviewDto.toReview(): Review{
    return Review(
        id = id,
        imageAuthor = imageAuthor,
        author = author,
        rating = rating,
        comment = comment,
        created = created
    )
}