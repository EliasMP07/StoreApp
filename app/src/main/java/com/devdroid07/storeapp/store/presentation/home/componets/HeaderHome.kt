package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.store.domain.model.Product

@Composable
fun HeaderHome(
    product: Product,
    onSearchClick: () -> Unit,
    onProductClick: (Product) -> Unit
){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick =onSearchClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search product",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Text(
            text = "Our Fashions App",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        ItemCardRecomendation(product = product, onProductClick = onProductClick)
        Spacer(modifier = Modifier.height(30.dp))
    }
}