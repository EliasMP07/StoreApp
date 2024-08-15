package com.devdroid07.storeapp.core.presentation.ui.util

import java.util.Locale

//Funcion de extension que reduce a dos decimas (0.00)
fun Double.roundToTwoDecimals(): Double {
    return String.format(Locale.ROOT, "%.2f", this).toDouble()
}
