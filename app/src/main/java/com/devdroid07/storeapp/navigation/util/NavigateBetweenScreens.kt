package com.devdroid07.storeapp.navigation.util

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
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

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED