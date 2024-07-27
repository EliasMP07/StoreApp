package com.devdroid07.storeapp.store.presentation.search

sealed interface SearchAction {
    data object OnSearch :SearchAction
    data class OnChangeQuery(val query: String): SearchAction
}