package com.devdroid07.storeapp.navigation.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.ui.unit.IntOffset


fun scaleIntoContainer(
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}


fun slideInAnimation(): EnterTransition {
    return slideIn(tween(1000)) { fullSize ->
        // Specifies the starting offset of the slide-in to be from the bottom right corner,
        // resulting in a simultaneous slide up and slide left.
        IntOffset(fullSize.width, fullSize.height)
    }
}



fun scaleOutOfContainer(
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ),
    ) + fadeOut(tween(delayMillis = 90))
}