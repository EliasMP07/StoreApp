package com.devdroid07.storeapp.navigation.util

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

fun NavHostController.navigateScreen(navBackStackEntry: NavBackStackEntry, route: String){
    if (navBackStackEntry.lifecycleIsResumed()){
        navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}
fun NavHostController.navigateToSingleTop(navBackStackEntry: NavBackStackEntry, route: String) {
    if (navBackStackEntry.lifecycleIsResumed()){
        navigate(route) {
            popUpTo(graph.id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
fun NavHostController.navigateToSingleInclusive(navBackStackEntry: NavBackStackEntry, route: String){
    if (navBackStackEntry.lifecycleIsResumed()){
        navigate(route) {
            popUpTo(route) { inclusive = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED