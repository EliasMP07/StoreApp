package com.devdroid07.storeapp.store.presentation.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.navigation.util.RoutesScreens

val drawerItems = listOf(
    DrawerItem(
        title = R.string.home,
        icon = R.drawable.ic_home,
        route = RoutesScreens.Favorite
    ),
    DrawerItem(
        title = R.string.search,
        icon = R.drawable.ic_search,
        route = RoutesScreens.Favorite
    ),
    DrawerItem(
        title = R.string.favorite,
        icon = R.drawable.ic_favorite,
        route = RoutesScreens.Favorite
    ),
    DrawerItem(
        title = R.string.account,
        icon = R.drawable.ic_account,
        route = RoutesScreens.Settings
    ),
    DrawerItem(
        title = R.string.settings,
        icon = R.drawable.ic_settings,
        route = RoutesScreens.Settings
    )
)

data class DrawerItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: RoutesScreens
)