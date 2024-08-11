package com.devdroid07.storeapp.store.presentation.home.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.store.presentation.home.navigationDrawer.utils.DrawerItem

@Composable
internal fun DrawerContent(
    drawerItems: List<DrawerItem>,
    currentRoute: RoutesScreens,
    modifier: Modifier = Modifier,
    onDrawerItemClick: (RoutesScreens) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.content_description_app),
            modifier = Modifier.size(200.dp)
        )

        drawerItems.forEach {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = it.title),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = it.icon),
                        contentDescription = stringResource(id = it.contentDescription),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Transparent
                ),
                selected = currentRoute == it.route,
                onClick = { onDrawerItemClick(it.route) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = stringResource(R.string.about_app),
            style = MaterialTheme.typography.titleSmall
        )
    }
}