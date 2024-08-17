package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.SnackBarStyle
import com.devdroid07.storeapp.core.presentation.ui.UiText
import kotlinx.coroutines.delay

/**
 * Snackbar custom para mostrar mensajes en pantalla pero personalizado
 *
 * @param modifier Modificador para aplicar al compposable
 * @param snackbarData La infomacion que se recibe del snackbar
 * @param labelButton Opcion por si se desea aplicar un botton clickable al snackbar
 */
@Composable
fun StoreSnackBar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData,
    labelButton: UiText? = null,
) {
    val style = SnackBarStyle.typeStyle(snackbarData.visuals.actionLabel?:"")
    val totalDuration = remember(style.duration) { style.duration * 500 }
    var millisRemaining by remember { mutableIntStateOf(totalDuration) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = style.backgroundColor,
        shadowElevation = 8.dp,
        modifier = modifier.padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp),
        ) {
            //Launch effecte que esconde el snackbar
            LaunchedEffect(snackbarData) {
                while (millisRemaining > 0) {
                    delay(20)
                    millisRemaining -= 20
                }
                snackbarData.dismiss()
            }
            style.icon?.let {
                Icon(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    imageVector = it,
                    contentDescription = snackbarData.visuals.actionLabel,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = snackbarData.visuals.message,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.weight(1f))
            if (style != SnackBarStyle.Error && labelButton != null) {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        pushStringAnnotation(
                            tag = "clickable_text",
                            annotation = labelButton.asString()
                        )
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        ) {
                            append(labelButton.asString())
                        }
                    }
                }
                ClickableText(
                    modifier = Modifier,
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            snackbarData.performAction()
                        }
                    }
                )
            }

        }
    }
}