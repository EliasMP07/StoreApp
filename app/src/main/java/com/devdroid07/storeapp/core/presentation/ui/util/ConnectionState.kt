package com.devdroid07.storeapp.core.presentation.ui.util

sealed class ConnectionState{
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}