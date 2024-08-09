package com.devdroid07.storeapp.core.presentation.designsystem

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.floor


/**
 * Clase que define una forma personalizada con "dientes" en la parte superior e inferior del borde para que tenga forma
 * de ticket
 *
 * @param teethHeightDp Altura de cada diente en dp.
 * @param teethWidthDp ncho de cada diente en dp.
 */
class TicketShapePay(
    private val teethWidthDp: Float,
    private val teethHeightDp: Float
) : Shape {

    // Crea el contorno de la forma.
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {

        // Empieza en la esquina superior derecha.
        moveTo(
            size.width * 0.99f,
            size.height * 0.01f
        )

        val teethHeightPx = teethHeightDp * density.density
        var fullTeethWidthPx = teethWidthDp * density.density
        var halfTeethWidthPx = fullTeethWidthPx / 2
        var currentDrawPositionX = size.width * 0.99f
        var teethBasePositionY = size.height * 0.01f + teethHeightPx
        val shapeWidthPx = size.width * 0.99f - size.width * 0.01f

        val teethCount = shapeWidthPx / fullTeethWidthPx
        val minTeethCount = floor(teethCount)

        // Lógica para encontrar el número óptimo de dientes para ajustar el ancho disponible sin desbordar
        // o subdimensionar los dientes, modificando el ancho de los dientes.
        if (teethCount != minTeethCount) { // Verifica si el ancho de la forma es múltiplo del número de dientes
            val newTeethWidthPx = shapeWidthPx / minTeethCount
            fullTeethWidthPx = newTeethWidthPx
            halfTeethWidthPx = fullTeethWidthPx / 2
        }

        var drawnTeethCount = 1 // Considerando que dibujaremos la mitad del primer y último diente.

        // Dibuja la mitad del primer diente.
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // Dibuja los dientes restantes.
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX -= halfTeethWidthPx

            // Dibuja la mitad derecha del diente.
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            currentDrawPositionX -= halfTeethWidthPx

            // Dibuja la mitad izquierda del diente.
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX -= halfTeethWidthPx

        // Dibuja la mitad del último diente.
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // Dibuja el borde izquierdo.
        lineTo(
            size.width * 0.01f,
            size.height * 0.99f
        )

        drawnTeethCount = 1
        teethBasePositionY = size.height * 0.99f - teethHeightPx
        currentDrawPositionX = size.width * 0.01f

        // Dibuja la mitad del primer diente.
        lineTo(
            currentDrawPositionX,
            teethBasePositionY + teethHeightPx
        )

        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // Dibuja los dientes restantes.
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX += halfTeethWidthPx

            // Dibuja la mitad izquierda del diente.
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            currentDrawPositionX += halfTeethWidthPx

            // Dibuja la mitad derecha del diente.
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX += halfTeethWidthPx

        // Dibuja la mitad del último diente.
        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // El borde izquierdo se dibujará automáticamente para cerrar el camino con el arco superior izquierdo.
        close()
    })

}

/**
 * Clase que define una forma personalizada con esquinas redondeadas y "cortes" circulares en los lados izquierdo y derecho.
 *
 * @param circleRadius Radio de los cortes circulares
 * @param cornerSize Tamaño de las esquina redondeandas
 */
class TicketShape(
    private val circleRadius: Dp,
    private val cornerSize: CornerSize,
) : Shape {

    // Crea el contorno de la forma.
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            path = getPath(
                size,
                density
            )
        )
    }

    // Genera el camino que representa la forma completa, combinando el rectángulo redondeado y los cortes circulares.
    private fun getPath(
        size: Size,
        density: Density,
    ): Path {
        val roundedRect = RoundRect(
            size.toRect(),
            CornerRadius(
                cornerSize.toPx(
                    size,
                    density
                )
            )
        )
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(
                size,
                density
            )
        )
    }

    // Genera el camino que representa solo los cortes circulares.
    private fun getTicketPath(
        size: Size,
        density: Density,
    ): Path {
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        val middleY = size.height / 2
        val horizontalCenter = size.width / 2

        return Path().apply {
            reset()
            // Empieza a dibujar desde la esquina superior izquierda.
            moveTo(
                x = 0F,
                y = 0F
            )
            // Dibuja una línea hasta la esquina superior derecha.
            lineTo(
                x = size.width,
                y = 0F
            )
            // Dibuja una línea hacia abajo por el lado derecho.
            lineTo(
                x = size.width,
                y = middleY - circleRadiusInPx
            )
            // Dibuja el arco derecho.
            arcTo(
                rect = Rect(
                    left = size.width - circleRadiusInPx,
                    top = middleY - circleRadiusInPx,
                    right = size.width + circleRadiusInPx,
                    bottom = middleY + circleRadiusInPx
                ),
                startAngleDegrees = 270F,
                sweepAngleDegrees = -180F,
                forceMoveTo = false
            )
            // Continúa la línea hasta la esquina inferior derecha.
            lineTo(
                x = size.width,
                y = size.height
            )
            // Dibuja una línea hasta la esquina inferior izquierda.
            lineTo(
                x = 0F,
                y = size.height
            )
            // Dibuja una línea hacia arriba por el lado izquierdo.
            lineTo(
                x = 0F,
                y = middleY + circleRadiusInPx
            )
            // Dibuja el arco izquierdo.
            arcTo(
                rect = Rect(
                    left = -circleRadiusInPx,
                    top = middleY - circleRadiusInPx,
                    right = circleRadiusInPx,
                    bottom = middleY + circleRadiusInPx
                ),
                startAngleDegrees = 90F,
                sweepAngleDegrees = -180F,
                forceMoveTo = false
            )
            // Cierra el camino dibujando una línea hasta la esquina superior izquierda.
            lineTo(
                x = 0F,
                y = 0F
            )
        }
    }
}
