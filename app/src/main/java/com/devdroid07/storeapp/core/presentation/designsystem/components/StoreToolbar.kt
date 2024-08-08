@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class,
    ExperimentalGlideComposeApi::class
)

package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.devdroid07.storeapp.R

@Composable
fun StoreToolbar(
    isMenu: Boolean = true,
    profile: String = "",
    title: String = "",
    onAccountClick: () -> Unit = {},
    openDrawer: () -> Unit = {},
    isProfile: Boolean = true,
    onBack: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (isMenu) {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu"
                    )
                }
            } else {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.content_description_navigationBack)
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
            )
        },
        actions = {
            if (isProfile) {
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
                        ).clickable {
                            onAccountClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Crop,
                        model = profile.ifBlank { R.drawable.ic_account },
                        transition = CrossFade,
                        contentDescription = "Profile Photo"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            } else {
                navigationIcon()
            }
        }
    )
}