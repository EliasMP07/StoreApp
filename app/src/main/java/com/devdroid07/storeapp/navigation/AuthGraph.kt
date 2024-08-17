package com.devdroid07.storeapp.navigation

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.devdroid07.storeapp.auth.presentation.intro.IntroScreenRoot
import com.devdroid07.storeapp.auth.presentation.login.LoginScreenRoot
import com.devdroid07.storeapp.auth.presentation.login.LoginViewModel
import com.devdroid07.storeapp.auth.presentation.register.RegisterScreenRoot
import com.devdroid07.storeapp.auth.presentation.register.RegisterViewModel
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.navigation.util.navigateScreen
import com.devdroid07.storeapp.navigation.util.navigateAndRemoveCurrent
import com.devdroid07.storeapp.navigation.util.navigateToSingleTop

fun NavGraphBuilder.auth(
    context: Context,
    navController: NavHostController,
) {
    navigation(
        startDestination = RoutesScreens.Intro.route,
        route = RoutesScreens.Auth.route
    ) {
        composable(
            route = RoutesScreens.Intro.route
        ) { navBackEntry ->
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Register.route
                    )
                },
                onSignInClick = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Login.route
                    )
                }
            )
        }
        composable(
            route = RoutesScreens.Login.route
        ) { navBackEntry ->

            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreenRoot(
                viewModel = viewModel,
                context = context,
                navigateToHome = {
                    navController.navigateToSingleTop(
                        navBackEntry,
                        RoutesScreens.Store.route
                    )
                },
                navigateToRegister = {
                    navController.navigateAndRemoveCurrent(
                        navBackEntry,
                        RoutesScreens.Register.route
                    )
                }
            )
        }
        composable(
            route = RoutesScreens.Register.route
        ) { navBackEntry ->

            val viewModel: RegisterViewModel = hiltViewModel()

            RegisterScreenRoot(
                viewModel = viewModel,
                context = context,
                navigateToRegister = {
                    navController.navigateAndRemoveCurrent(
                        navBackEntry,
                        RoutesScreens.Login.route
                    )
                },
                navigateToHome = {
                    navController.navigateToSingleTop(
                        navBackEntry,
                        RoutesScreens.Store.route
                    )
                }
            )
        }
    }
}