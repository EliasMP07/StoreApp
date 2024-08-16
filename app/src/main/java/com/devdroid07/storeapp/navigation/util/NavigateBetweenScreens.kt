package com.devdroid07.storeapp.navigation.util

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * Navega a la ruta especificada solo si el NavBackStackEntry está en un estado reanudado.
 *
 * @param navBackStackEntry La entrada actual de la pila de navegación. Se utiliza para verificar el estado del ciclo de vida.
 * @param route La ruta de destino a la que se desea navegar.
 */
fun NavHostController.navigateScreen(
    navBackStackEntry: NavBackStackEntry,
    route: String,
) {
    if (navBackStackEntry.lifecycleIsResumed()) {
        navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

/**
 * Navega a la ruta especificada, asegurándose de que sea la única entrada en la cima de la pila de navegación.
 *
 * @param navBackStackEntry La entrada actual de la pila de navegación. Se utiliza para verificar el estado del ciclo de vida.
 * @param route La ruta de destino a la que se desea navegar.
 */
fun NavHostController.navigateToSingleTop(
    navBackStackEntry: NavBackStackEntry,
    route: String,
) {
    if (navBackStackEntry.lifecycleIsResumed()) {
        navigate(route) {
            popUpTo(graph.id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}

/**
 * Navega a la ruta especificada, eliminando la ruta actual de la pila de navegación.
 *
 * @param navBackStackEntry La entrada actual de la pila de navegación. Se utiliza para verificar el estado del ciclo de vida.
 * @param route La ruta de destino a la que se desea navegar.
 */
fun NavHostController.navigateAndRemoveCurrent(
    navBackStackEntry: NavBackStackEntry,
    route: String,
) {
    if (navBackStackEntry.lifecycleIsResumed()) {
        val currentRoute = navBackStackEntry.destination.route
        if (currentRoute != null) {
            navigate(route) {
                popUpTo(currentRoute) { inclusive = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

/**
 * Función de extensión para verificar si el ciclo de vida del NavBackStackEntry está en el estado reanudado.
 *
 * @return true si el ciclo de vida está en el estado RESUMED, false en caso contrario.
 */
fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED