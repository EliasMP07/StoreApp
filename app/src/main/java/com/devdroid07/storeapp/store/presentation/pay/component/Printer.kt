package com.devdroid07.storeapp.store.presentation.pay.component


import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import kotlinx.coroutines.delay

/**
 * Composable con forma de impresora de tickets
 *
 * @param modifier Modificador para el composable
 */
@Composable
fun Printer(
    modifier: Modifier = Modifier,
) {
    //Variable que contiene el estado del color
    var buttonColor by remember { mutableStateOf(Color(0xFF1A1A1A)) }
    val context = LocalContext.current

    // Inicializa el MediaPlayer para el audio de imprimir
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.ticket_print)
    }



    // Inicia el sonido y cambia el color del boton de imprimir simulando la impresion
    LaunchedEffect(Unit) {
        mediaPlayer.start()
        repeat(3) {
            buttonColor = Color.Green
            delay(500)
            buttonColor = Color(0xFF1A1A1A)
            delay(500)
        }
        buttonColor = Color(0xFF1A1A1A)
    }

    // Asegura de que el MediaPlayer se libere cuando el elemento composable abandone la composición
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    //Vista de impresora
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .size(
                width = 160.dp,
                height = 140.dp
            )
            .background(
                color = Color(0xFF4A4A4A),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
                .height(8.dp)
                .background(
                    Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-24).dp)
                .size(
                    width = 80.dp,
                    height = 50.dp
                )
                .background(
                    Color(0xFFFFE0E0),
                    shape = CutCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.7f)
                .height(12.dp)
                .background(
                    Color(0xFFE54B4B),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(12.dp)
                .background(
                    buttonColor,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}

@Composable
private fun PrinterPreview(){
    StoreAppTheme {
        Printer()
    }
}