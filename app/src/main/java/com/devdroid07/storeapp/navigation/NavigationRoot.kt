package com.devdroid07.storeapp.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.devdroid07.storeapp.auth.presentation.intro.IntroScreenRoot
import com.devdroid07.storeapp.auth.presentation.login.LoginEvent
import com.devdroid07.storeapp.auth.presentation.login.LoginScreenRoot
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.RegisterEvent
import com.devdroid07.storeapp.auth.presentation.register.RegisterScreenRoot
import com.devdroid07.storeapp.auth.presentation.register.RegisterViewModel
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.navigation.util.scaleIntoContainer
import com.devdroid07.storeapp.navigation.util.scaleOutOfContainer
import com.devdroid07.storeapp.store.presentation.home.HomeAction
import com.devdroid07.storeapp.store.presentation.home.HomeScreenRoot
import com.devdroid07.storeapp.store.presentation.home.HomeViewModel
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailRootScreenRoot

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
        auth(context, navController)
        store(navController)
    }
}

fun NavGraphBuilder.store(
    navController: NavHostController
) {
    navigation(
        route = RoutesScreens.Store.route,
        startDestination = "home"
    ) {
        composable(
            route = RoutesScreens.Home.route
        ) {

            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeScreenRoot(
                state = state,
                onAction = { action ->
                    when (action) {
                        is HomeAction.OnProductDetailScreen -> {
                            navController.navigate(RoutesScreens.DetailProduct.createRoute(action.idProduct))
                        }
                        else -> Unit
                    }
                    viewModel.onAction(action)
                }
            )
        }
        composable(
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer()
            },
            route = RoutesScreens.DetailProduct.route,
            arguments = listOf(navArgument(NavArgs.ProductID.key) { type = NavType.StringType })

        ) {
            ProductDetailRootScreenRoot(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}


fun NavGraphBuilder.auth(
    context: Context,
    navController: NavHostController
) {
    navigation(
        startDestination = "intro",
        route = RoutesScreens.Auth.route
    ) {
        composable(
            route = "intro"
        ) {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(
            route = "login"
        ) {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(RoutesScreens.Store.route) {
                        popUpTo(RoutesScreens.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = "register"
        ) {

            val viewModel: RegisterViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val keyboardController = LocalSoftwareKeyboardController.current

            RegisterScreenRoot(
                state = state,
                onAction = {action ->
                    when(action){
                        RegisterAction.OnLoginClick -> {
                            navController.navigate("login") {
                                popUpTo("register") {
                                    inclusive = true
                                    saveState = true
                                }
                                restoreState = true
                            }
                        }
                        else -> Unit
                    }
                    viewModel.onAction(action)
                }
            )
            ObserveAsEvents(viewModel.events) { event ->
                when (event) {
                    is RegisterEvent.Error -> {
                        keyboardController?.hide()
                        Toast.makeText(
                            context,
                            event.message.asString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    RegisterEvent.OnRegisterSuccess -> {
                        keyboardController?.hide()
                        Toast.makeText(
                            context,
                            "Cuenta registrada con exito",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(RoutesScreens.Store.route) {
                            popUpTo(RoutesScreens.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}