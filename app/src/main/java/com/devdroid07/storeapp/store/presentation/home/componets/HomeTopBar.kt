@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.animation.shimmerEffect
import com.devdroid07.storeapp.core.presentation.designsystem.components.LoadImageCoil

@Composable
fun HomeTopBar(
    profile: String = "",
    spacing: Dimensions,
    onAccountClick: () -> Unit = {},
    onSearchClick: () -> Unit,
    openDrawer: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.welcome_app),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(R.string.our_fashions_app),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onSearchClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.content_description_search_product),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.onBackground
                        ),
                        CircleShape
                    )
                    .clickable {
                        onAccountClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                LoadImageCoil(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = profile.ifBlank { R.drawable.ic_account },
                    contentLoading = {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect())
                    },
                    contentDescription = R.string.content_description_profile
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(R.string.content_description_menu)
                )
            }
        }
    )
}