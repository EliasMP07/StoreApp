package com.devdroid07.storeapp.store.domain.model

data class Review(
    val id: Int,
    val imageAuthor: String,
    val author: String,
    val rating: Double,
    val comment: String,
    val created: String
)
