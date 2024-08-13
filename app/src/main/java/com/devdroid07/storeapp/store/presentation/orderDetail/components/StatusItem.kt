package com.devdroid07.storeapp.store.presentation.orderDetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.store.presentation.orderDetail.components.utils.StatusOrder

@Composable
fun StatusItem(
    status: StatusOrder,
    currentStatus: StatusOrder,
) {
    val isCurrent = status == currentStatus
    val alpha = if (isCurrent) 1f else 0.3f

    Column(
        modifier = Modifier
            .alpha(alpha)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = status.title),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = status.icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
        if (isCurrent) {
            Text(
                text = stringResource(id = status.description),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

