package com.devdroid07.storeapp.store.presentation.pay.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.CheckIcon
import com.devdroid07.storeapp.core.presentation.designsystem.CloseIcon
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreActionButton
import com.devdroid07.storeapp.store.presentation.pay.FinishPayAction
import com.devdroid07.storeapp.store.presentation.pay.component.utils.Progress
import com.devdroid07.storeapp.store.presentation.pay.component.utils.ProgressPay

@Composable
fun DialogProgressPay(
    progress: ProgressPay = ProgressPay.LOADING,
    onAction: (FinishPayAction) -> Unit,
) {
    val progressState = getProgressState(progress)

    Dialog(onDismissRequest = {

    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .animateContentSize(),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 40.dp,
                            bottom = 32.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = progressState.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = progressState.message),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Crossfade(
                        targetState = progress,
                        label = "",
                        animationSpec = tween(2000)
                    ) { progress ->
                        when (progress) {
                            ProgressPay.FAILURE -> {
                                StoreActionButton(
                                    text = "Cambiar",
                                    isLoading = false
                                ) {
                                    onAction(FinishPayAction.OnChangeOtherCardClick)
                                }
                            }
                            else -> Unit
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibilityTransitionLogo(progress = progress)
            }
        }
    }
}


@Composable
fun getProgressState(progress: ProgressPay): Progress {
    return when (progress) {
        ProgressPay.LOADING -> Progress(
            hasButton = false,
            title = R.string.loading_progresspay_title,
            message = R.string.message_progresspay_message
        )
        ProgressPay.FAILURE -> Progress(
            hasButton = true,
            title = R.string.failure_progresspay_title,
            message = R.string.failure_progresspay_message
        )
        ProgressPay.SUCCESS -> Progress(
            hasButton = true,
            title = R.string.success_progresspay_title,
            message = R.string.success_progresspay_message
        )
    }
}
