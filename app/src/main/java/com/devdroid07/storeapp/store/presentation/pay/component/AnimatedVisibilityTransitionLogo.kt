package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay

@Composable
fun AnimatedVisibilityTransitionLogo(progress: ProgressPay) {
    val states = listOf(
        ProgressPay.LOADING,
        ProgressPay.FAILURE,
        ProgressPay.SUCCESS
    )
    states.forEach { state ->
        AnimatedVisibility(
            visible = progress == state,
            enter = scaleIn(animationSpec = tween(1000)),
            exit = scaleOut(animationSpec = tween(1000))
        ) {
            when (state) {
                ProgressPay.LOADING -> ProgressLogoIndicator(background = Color.White) {
                    CircularProgressIndicator()
                }
                ProgressPay.FAILURE -> ProgressLogoIndicator(background = Color.Red) {
                    Icon(
                        imageVector = CloseIcon,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                ProgressPay.SUCCESS -> ProgressLogoIndicator(background = Color.Green) {
                    Icon(
                        imageVector = CheckIcon,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}