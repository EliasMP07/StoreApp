package com.devdroid07.storeapp.core.domain.util

sealed interface DataError: Error{
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
}