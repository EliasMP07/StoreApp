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


class TicketShapePay(
    private val teethWidthDp: Float,
    private val teethHeightDp: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {

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

        // logic to find optimized count of teethes to fit the available width without overflowing
        // or underflowing teethes, by modifying the teeth width
        if (teethCount != minTeethCount) { // check to allow drawing if shape width is a multiple of teeth count
            val newTeethWidthPx = shapeWidthPx / minTeethCount
            fullTeethWidthPx = newTeethWidthPx
            halfTeethWidthPx = fullTeethWidthPx / 2
        }

        var drawnTeethCount = 1 // considering we will draw half of first and last teeth
        // statically we start with one teeth

        // draw half of first teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX -= halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            currentDrawPositionX -= halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX - halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX -= halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX - halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw left edge
        lineTo(
            size.width * 0.01f,
            size.height * 0.99f
        )

        drawnTeethCount = 1
        teethBasePositionY = size.height * 0.99f - teethHeightPx
        currentDrawPositionX = size.width * 0.01f

        // draw half of first teeth
        lineTo(
            currentDrawPositionX,
            teethBasePositionY + teethHeightPx
        )

        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY - teethHeightPx
        )

        // draw remaining teethes
        while (drawnTeethCount < minTeethCount) {

            currentDrawPositionX += halfTeethWidthPx

            // draw left half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY + teethHeightPx
            )

            currentDrawPositionX += halfTeethWidthPx

            // draw right half of teeth
            lineTo(
                currentDrawPositionX + halfTeethWidthPx,
                teethBasePositionY - teethHeightPx
            )

            drawnTeethCount++
        }

        currentDrawPositionX += halfTeethWidthPx

        // draw half of last teeth
        lineTo(
            currentDrawPositionX + halfTeethWidthPx,
            teethBasePositionY + teethHeightPx
        )

        // left edge will automatically be drawn to close the path with the top-left arc
        close()
    })

}

class TicketShape(
    private val circleRadius: Dp,
    private val cornerSize: CornerSize,
) : Shape {

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

    private fun getTicketPath(
        size: Size,
        density: Density,
    ): Path {
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        val middleY = size.height / 2
        val horizontalCenter = size.width / 2

        return Path().apply {
            reset()
            // Start drawing from top left
            moveTo(
                x = 0F,
                y = 0F
            )
            // Draw line to top right
            lineTo(
                x = size.width,
                y = 0F
            )
            // Draw line down the right side
            lineTo(
                x = size.width,
                y = middleY - circleRadiusInPx
            )
            // Draw the right arc
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
            // Continue line to bottom right corner
            lineTo(
                x = size.width,
                y = size.height
            )
            // Draw line to bottom left
            lineTo(
                x = 0F,
                y = size.height
            )
            // Draw line up the left side
            lineTo(
                x = 0F,
                y = middleY + circleRadiusInPx
            )
            // Draw the left arc
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
            // Close the path by drawing line to the top left corner
            lineTo(
                x = 0F,
                y = 0F
            )
        }
    }
}

