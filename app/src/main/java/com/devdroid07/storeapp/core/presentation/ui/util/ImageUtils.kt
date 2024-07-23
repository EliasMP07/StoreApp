package com.devdroid07.storeapp.core.presentation.ui.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

fun imageCamara(image: String): String{
    return File(image).readBytes().toBase64()
}

private fun ByteArray.toBase64(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}


fun reduceImageSize(imagePath: String): String {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(imagePath, options)

    // Calcula el factor de reducción
    val targetWidth = 600  // ancho objetivo más pequeño
    val targetHeight = (options.outHeight * targetWidth / options.outWidth).coerceAtLeast(1)
    val scaleFactor = (options.outWidth / targetWidth).coerceAtLeast(1)

    // Decodifica la imagen con el factor de reducción
    options.inJustDecodeBounds = false
    options.inSampleSize = scaleFactor

    val bitmap = BitmapFactory.decodeFile(imagePath, options)

    // Redimensiona la imagen
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

    // Comprime la imagen y la convierte a Base64
    val byteArrayOutputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream) // Comprimir con calidad del 70%
    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}