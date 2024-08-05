package com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils

class IssuerFinder {

    companion object {
        fun detect(number: String): CardIssuer = when {
            isVisa(number) -> CardIssuer.VISA
            isMastercard(number) -> CardIssuer.MASTERCARD
            else -> CardIssuer.EMPTY
        }

        private fun isVisa(number: String) = number.isNotEmpty() && number.first() == '4'

        private fun isMastercard(number: String) = number.length >= 2 && number.substring(
            0,
            2
        ).toIntOrNull() in 51 until 56

    }
}