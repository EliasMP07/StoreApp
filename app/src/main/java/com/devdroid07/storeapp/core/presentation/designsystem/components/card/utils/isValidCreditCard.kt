package com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils

fun isValidCreditCard(number: String): Boolean {
    if (number.length != 16 || !number.all { it.isDigit() }) {
        return false
    }

    fun luhnCheck(cardNumber: String): Boolean {
        val reversedDigits = cardNumber.reversed().map { it.toString().toInt() }
        val sum = reversedDigits.mapIndexed { index, digit ->
            if (index % 2 == 1) {
                val doubled = digit * 2
                if (doubled > 9) doubled - 9 else doubled
            } else {
                digit
            }
        }.sum()
        return sum % 10 == 0
    }

    return luhnCheck(number)
}