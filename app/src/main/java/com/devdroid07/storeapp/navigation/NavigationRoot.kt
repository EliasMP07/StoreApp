package com.devdroid07.storeapp.navigation

import android.content.Context
import android.widget.Toast
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
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.auth.presentation.intro.IntroScreenRoot
import com.devdroid07.storeapp.auth.presentation.login.LoginScreenRoot
import com.devdroid07.storeapp.auth.presentation.register.RegisterAction
import com.devdroid07.storeapp.auth.presentation.register.RegisterEvent
import com.devdroid07.storeapp.auth.presentation.register.RegisterScreenRoot
import com.devdroid07.storeapp.auth.presentation.register.RegisterViewModel
import com.devdroid07.storeapp.core.presentation.ui.ObserveAsEvents
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.navigation.util.lifecycleIsResumed
import com.devdroid07.storeapp.navigation.util.navigateBack
import com.devdroid07.storeapp.navigation.util.navigateScreen
import com.devdroid07.storeapp.navigation.util.navigateTo
import com.devdroid07.storeapp.navigation.util.scaleIntoContainer
import com.devdroid07.storeapp.navigation.util.scaleOutOfContainer
import com.devdroid07.storeapp.store.presentation.address.AddressScreenRoot
import com.devdroid07.storeapp.store.presentation.address.AddressViewModel
import com.devdroid07.storeapp.store.presentation.home.componets.HomeDrawerScreens
import com.devdroid07.storeapp.store.presentation.myCart.MyCartScreenRoot
import com.devdroid07.storeapp.store.presentation.myCart.MyCartViewModel
import com.devdroid07.storeapp.store.presentation.payment.PaymentScreenRoot
import com.devdroid07.storeapp.store.presentation.payment.PaymentViewModel
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailRootScreenRoot
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailViewModel
import com.devdroid07.storeapp.store.presentation.search.SearchScreenRoot
import com.devdroid07.storeapp.store.presentation.search.SearchViewModel

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

fun NavGraphBuilder.store(
    context: Context,
    navController: NavHostController,
) {
    navigation(
        route = RoutesScreens.Store.route,
        startDestination = RoutesScreens.HomeDrawerRoute.route
    ) {
        composable(
            route = RoutesScreens.HomeDrawerRoute.route
        ) { navBackEntry ->
            HomeDrawerScreens(
                context = context,
                navigateToSettings = {},
                navigateToDetailProduct = {
                    if (navBackEntry.lifecycleIsResumed()) {
                        navController.navigate(RoutesScreens.DetailProduct.createRoute(it))
                    }
                },
                navigateSearch = {
                    if (navBackEntry.lifecycleIsResumed()) {
                        navController.navigate(RoutesScreens.Search.route)
                    }
                },
                navigateMyCart = {
                    if (navBackEntry.lifecycleIsResumed()) {
                        navController.navigate(RoutesScreens.Cart.route)
                    }
                }
            )
        }

        composable(
            route = RoutesScreens.Cart.route
        ) { navBackEntry ->

            val viewModel: MyCartViewModel = hiltViewModel()
            val onAction = viewModel::onAction
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyCartScreenRoot(
                state = state,
                context = context,
                viewModel = viewModel,
                navigateToPay = {
                    if (navBackEntry.lifecycleIsResumed()) {
                        navController.navigateTo(RoutesScreens.Address)
                    }
                },
                navigateBack = {
                    navController.navigateBack()
                },
                onAction = onAction,
            )

        }
        composable(
            route = RoutesScreens.Search.route
        ) {

            val viewModel: SearchViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction

            SearchScreenRoot(
                state = state,
                navigateDetailProduct = {
                    navController.navigate(RoutesScreens.DetailProduct.createRoute(it))
                },
                onAction = onAction
            )

        }

        composable(
            route = RoutesScreens.Address.route
        ) { navBackEntry ->

            val viewModel: AddressViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction

            AddressScreenRoot(
                state = state,
                context = context,
                viewModel = viewModel,
                onAction = onAction,
                navigateToPayment = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Payment.createRoute(it.toString())
                    )
                },
                onBack = {
                    navController.navigateBack()
                }
            )
        }

        composable(
            route = RoutesScreens.Payment.route
        ) {navBackEntry ->
            val viewModel: PaymentViewModel = hiltViewModel()

            PaymentScreenRoot(
                context = context,
                viewModel = viewModel,
                onBack = {
                    navController.navigateBack()
                },
                navigateToFinishPay = {addressId, cardId ->
                    navController.navigateScreen(navBackEntry, RoutesScreens.HomeDrawerRoute.route)
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

            val viewModel: ProductDetailViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val onAction = viewModel::onAction

            ProductDetailRootScreenRoot(
                state = state,
                context = context,
                viewModel = viewModel,
                onAction = onAction,
                onBack = {
                    navController.navigateBack()
                }
            )

        }
    }
}


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
        ) {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate(RoutesScreens.Register.route)
                },
                onSignInClick = {
                    navController.navigate(RoutesScreens.Login.route)
                }
            )
        }
        composable(
            route = RoutesScreens.Login.route
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
                    navController.navigate(RoutesScreens.Register.route) {
                        popUpTo(RoutesScreens.Login.route) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = RoutesScreens.Register.route
        ) {

            val viewModel: RegisterViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val keyboardController = LocalSoftwareKeyboardController.current

            RegisterScreenRoot(
                state = state,
                onAction = { action ->
                    when (action) {
                        RegisterAction.OnLoginClick -> {
                            navController.navigate(RoutesScreens.Login.route) {
                                popUpTo(RoutesScreens.Register.route) {
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
                    RegisterEvent.RegisterSuccess -> {
                        keyboardController?.hide()
                        Toast.makeText(
                            context,
                            context.getString(R.string.account_create_success),
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