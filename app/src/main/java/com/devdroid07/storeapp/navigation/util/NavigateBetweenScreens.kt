package com.devdroid07.storeapp.navigation.util

import androidx.navigation.NavHostController

fun NavHostController.navigateTo(route: RoutesScreens) {
    navigate(route.route) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToSingleTop(route: RoutesScreens) {
    navigate(route.route) {
        popUpTo(graph.id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}