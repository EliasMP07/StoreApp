package com.devdroid07.storeapp.store.presentation.pay.component.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Funcion que devuelve la fecha en que se hace el pago en un formato String
fun getFormattedDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd 'de' MMM yyyy, hh:mm a", Locale("es", "ES"))
    return dateFormat.format(date)
}

//Funcion que asigna el string de la fecha
fun getCurrentFormattedDate(): String {
    val currentDate = Date()
    return getFormattedDate(currentDate)
}
