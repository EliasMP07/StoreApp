package com.devdroid07.storeapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devdroid07.storeapp.navigation.util.RoutesScreens

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    context: Context,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) RoutesScreens.Store.route else RoutesScreens.Auth.route
    ) {
        auth(
            context,
            navController
        )
        store(
            navController = navController,
            context = context
        )
    }
}
