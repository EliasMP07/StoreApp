package com.devdroid07.storeapp.core.presentation.designsystem.components.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.animateOffset(): Modifier {
    val animation = rememberInfiniteTransition(label = "")
    val offsetIcon by animation.animateValue(
        typeConverter = Dp.VectorConverter,
        initialValue = (-5).dp,
        targetValue = 25.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    return this.offset(x = offsetIcon)
}

@Composable
fun Modifier.animateEnter(
    targetValue: Float = 0f,
    durationMillis: Int = 1000,
): Modifier {
    val animatable = remember { Animatable(1000f) }

    LaunchedEffect(animatable) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = durationMillis
            )
        )
    }

    return this.offset { IntOffset(animatable.value.toInt(), 0) }
}

@Composable
fun Modifier.animateAttention(
    targetValue: Float = 1f,
): Modifier {
    val animatable = remember { Animatable(0.1f) }
    LaunchedEffect(Unit) {
        animatable.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutBounce
            )
        )
    }
    return this.graphicsLayer {
        scaleX = animatable.value
        scaleY = animatable.value
    }
}


@Composable
fun CardDefaults.animationElevate(): CardElevation {
    val value by rememberInfiniteTransition().animateValue(
        initialValue = 0.dp,
        targetValue = 30.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            ),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter,
        label = ""
    )

    return this.cardElevation(
        defaultElevation = value,
        pressedElevation = value,
        focusedElevation = value,
        hoveredElevation = value,
        draggedElevation = value,
        disabledElevation = value,
    )
}