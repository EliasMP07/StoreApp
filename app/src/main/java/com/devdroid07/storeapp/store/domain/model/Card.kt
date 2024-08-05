package com.devdroid07.storeapp.store.domain.model

data class Card(
    val id: Int = 0,
    val cardNumber: String = "",
    val expireDate: String = "",
    val nameHeadline: String = "",
    val cvv: String = ""
)
