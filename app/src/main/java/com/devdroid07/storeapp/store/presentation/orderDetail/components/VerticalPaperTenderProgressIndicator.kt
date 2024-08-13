package com.devdroid07.storeapp.store.presentation.orderDetail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.store.presentation.orderDetail.components.utils.StatusOrder

@Composable
fun VerticalPaperTenderProgressIndicator(
    modifier: Modifier = Modifier,
    currentStatus: StatusOrder,
    statuses: List<StatusOrder>,
    height: Dp,
) {
    val currentIndex = statuses.indexOf(currentStatus)

    Canvas(
        modifier = modifier
            .height(height)
            .width(50.dp)
    ) {
        val totalHeight = size.height - 2 * 12.dp.toPx()
        val segmentHeight = totalHeight / (statuses.size - 1)
        val circleRadius = 12.dp.toPx()
        val shadowRadius = circleRadius * 1.5f

        // Dibujar sombras detrás de los círculos
        statuses.forEachIndexed { index, _ ->
            val offsetY = segmentHeight * index + circleRadius
            drawCircle(
                color = Color(0xFF929292),
                radius = shadowRadius,
                center = Offset(
                    size.width / 2,
                    offsetY
                )
            )
        }

        // Dibuja la linea que sera la sombra
        drawLine(
            color = Color(0xFF929292),
            start = Offset(
                size.width / 2,
                circleRadius
            ),
            end = Offset(
                size.width / 2,
                size.height - circleRadius
            ),
            strokeWidth = 13.dp.toPx(),
            cap = StrokeCap.Round
        )
        //Linea que representa cuanddo no hay progreso
        drawLine(
            color = Color(0xFFFFFFFF),
            start = Offset(
                size.width / 2,
                circleRadius
            ),
            end = Offset(
                size.width / 2,
                size.height - circleRadius
            ),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Dibujar la línea de progreso
        drawLine(
            color = Color(0xFF8FF303),
            start = Offset(
                size.width / 2,
                circleRadius
            ),
            end = Offset(
                size.width / 2,
                if (currentIndex == statuses.size - 1) size.height - circleRadius else segmentHeight * (currentIndex + 0.5f) + circleRadius
            ),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Dibujar los círculos principales
        statuses.forEachIndexed { index, _ ->
            val color = if (index <= currentIndex) Color(0xFF8FF303) else Color.White
            val offsetY = segmentHeight * index + circleRadius
            drawCircle(
                color = color,
                radius = circleRadius,
                center = Offset(
                    size.width / 2,
                    offsetY
                )
            )
        }
    }
}