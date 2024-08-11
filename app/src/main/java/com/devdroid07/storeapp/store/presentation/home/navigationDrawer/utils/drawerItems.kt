package com.devdroid07.storeapp.store.presentation.home.navigationDrawer.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.navigation.util.RoutesScreens

val drawerItems = listOf(
    DrawerItem(
        title = R.string.home,
        contentDescription = R.string.content_description_home_draw_item,
        icon = R.drawable.ic_home,
        route = RoutesScreens.Home
    ),
    DrawerItem(
        title = R.string.search,
        contentDescription = R.string.content_description_search_draw_item,
        icon = R.drawable.ic_search,
        route = RoutesScreens.Search
    ),
    DrawerItem(
        title = R.string.orders,
        contentDescription = R.string.content_description_orders_draw_item,
        icon = R.drawable.ic_badge,
        route = RoutesScreens.Orders
    ),
    DrawerItem(
        title = R.string.favorite,
        contentDescription = R.string.content_description_favorite_draw_item,
        icon = R.drawable.ic_favorite,
        route = RoutesScreens.Favorite
    ),
    DrawerItem(
        title = R.string.account,
        contentDescription = R.string.content_description_account_draw_item,
        icon = R.drawable.ic_account,
        route = RoutesScreens.Account
    ),
    DrawerItem(
        title = R.string.settings,
        contentDescription = R.string.content_description_configure_draw_item,
        icon = R.drawable.ic_settings,
        route = RoutesScreens.Settings
    )
)

data class DrawerItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
    val route: RoutesScreens
)