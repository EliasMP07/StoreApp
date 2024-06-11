package com.devdroid07.storeapp.auth.data.matcher

import android.util.Patterns
import com.devdroid07.storeapp.auth.domain.repository.PatternValidator

class EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}