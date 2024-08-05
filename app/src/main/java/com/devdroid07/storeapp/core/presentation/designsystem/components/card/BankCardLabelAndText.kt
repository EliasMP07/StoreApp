package com.devdroid07.storeapp.core.presentation.designsystem.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BankCardLabelAndText(
    label: String,
    text: String,
    textColor: Color,
) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontWeight = FontWeight.W300,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                color = textColor
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                letterSpacing = 1.sp,
                color = textColor
            )
        )
    }
}