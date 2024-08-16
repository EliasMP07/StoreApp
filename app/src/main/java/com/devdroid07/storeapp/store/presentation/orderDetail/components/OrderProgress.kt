package com.devdroid07.storeapp.store.presentation.orderDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateEnterFromLeft
import com.devdroid07.storeapp.store.presentation.orderDetail.components.utils.StatusOrder


@Composable
fun OrderStatusProgress(
    modifier: Modifier = Modifier,
    currentStatus: StatusOrder,
) {
    val statuses = listOf(
        StatusOrder.Pendiente,
        StatusOrder.Despachando,
        StatusOrder.Encamino,
        StatusOrder.Entregado
    )
    Row(
        modifier = modifier
            .animateEnterFromLeft()
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VerticalPaperTenderProgressIndicator(
            currentStatus = currentStatus,
            height = 300.dp,
            statuses = statuses
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(300.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            statuses.forEachIndexed { _, status ->
                StatusItem(
                    status = status,
                    currentStatus = currentStatus
                )
            }
        }
    }
}