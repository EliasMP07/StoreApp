package com.devdroid07.storeapp.auth.domain.repository

interface PatternValidator {
    fun matches(value: String): Boolean
}