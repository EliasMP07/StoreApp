package com.devdroid07.storeapp.navigation

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.devdroid07.storeapp.auth.presentation.intro.IntroScreenRoot
import com.devdroid07.storeapp.auth.presentation.login.LoginScreenRoot
import com.devdroid07.storeapp.navigation.util.scaleIntoContainer
import com.devdroid07.storeapp.navigation.util.scaleOutOfContainer
import com.devdroid07.storeapp.store.presentation.home.HomeScreenRoot
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailRootScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        auth(navController)
        store(navController)
    }
}

fun NavGraphBuilder.store(
    navController: NavHostController
) {
    navigation(
        route = "store",
        startDestination = "home"
    ) {
        composable(
            route = "home"
        ) {
            HomeScreenRoot(
                onProductDetail = {
                    navController.navigate("product_detail")
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
            route = "product_detail"
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
    navController: NavHostController
) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(
            route = "intro"
        ) {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("login")
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

                }, onRegisterClick = {
                    navController.navigate("store") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}