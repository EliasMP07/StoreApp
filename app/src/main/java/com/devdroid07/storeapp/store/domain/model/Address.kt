package com.devdroid07.storeapp.store.domain.model

data class Address(
    val id: Int = 0,
    val street: String = "",
    val postalCode: String = "",
    val state: String = "",
    val mayoralty: String = "",
    val settlement: String = "",
    val phoneNumber: String = "",
    val reference: String = ""
)
