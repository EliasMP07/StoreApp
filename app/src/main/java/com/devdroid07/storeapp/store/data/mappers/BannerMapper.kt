package com.devdroid07.storeapp.store.data.mappers

import com.devdroid07.storeapp.store.data.network.dto.store.BannerDto
import com.devdroid07.storeapp.store.domain.model.Banner

fun BannerDto.toBanner(): Banner{
    return Banner(
        description = description,
        discountPercentage = discountPercentage,
        endDate = endDate,
        id = id,
        imageUrl = imageUrl,
        startDate = startDate,
        type = type
    )
}