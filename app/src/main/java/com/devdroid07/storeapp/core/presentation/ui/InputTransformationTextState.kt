@file:OptIn(ExperimentalFoundationApi::class)

package com.devdroid07.storeapp.core.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldCharSequence

class TextCardTransformation: InputTransformation {
    override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer,
    ) {
        val originalText = valueWithChanges.toString().filter { it.isDigit() }
        val trimmed = if (originalText.length > 16) originalText.substring(
            0,
            16
        ) else originalText

        val formatted = StringBuilder()

        for (i in trimmed.indices) {
            if (i == 4) formatted.append(" ")
            if (i == 8) formatted.append(" ")
            if (i == 12) formatted.append(" ")
            formatted.append(trimmed[i])
        }

        valueWithChanges.replace(
            0,
            valueWithChanges.length,
            formatted
        )
    }

}

class ExpiryDateInputTransformation : InputTransformation {

    override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer,
    ) {

        val originalText = valueWithChanges.toString().filter { it.isDigit() }
        val trimmed = if (originalText.length > 4) originalText.substring(
            0,
            4
        ) else originalText

        val formatted = StringBuilder()
        var month = 0

        for (i in trimmed.indices) {
            month += Character.getNumericValue(trimmed[i])
            when (i) {
                0 -> month = Character.getNumericValue(trimmed[i]) * 10
                1 -> {
                    month += Character.getNumericValue(trimmed[i])
                    if (month > 12) {
                        month = 12
                        formatted.clear()
                        formatted.setLength(0)
                        formatted.append("12")
                    } else {
                        formatted.append(trimmed[i])
                    }
                }
                2 -> {
                    formatted.setLength(i)
                    formatted.append("/")
                }
            }
            if (i != 1) {
                formatted.append(trimmed[i])
            }
        }


        valueWithChanges.replace(
            0,
            valueWithChanges.length,
            formatted
        )
    }

}
