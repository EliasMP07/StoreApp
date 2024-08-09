package com.devdroid07.storeapp.navigation

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.devdroid07.storeapp.navigation.util.NavArgs
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.navigation.util.lifecycleIsResumed
import com.devdroid07.storeapp.navigation.util.navigateBack
import com.devdroid07.storeapp.navigation.util.navigateScreen
import com.devdroid07.storeapp.navigation.util.navigateToSingleInclusive
import com.devdroid07.storeapp.navigation.util.navigateToSingleTop
import com.devdroid07.storeapp.navigation.util.scaleIntoContainer
import com.devdroid07.storeapp.navigation.util.scaleOutOfContainer
import com.devdroid07.storeapp.store.presentation.address.AddressScreenRoot
import com.devdroid07.storeapp.store.presentation.address.AddressViewModel
import com.devdroid07.storeapp.store.presentation.home.navigationDrawer.HomeDrawerScreens
import com.devdroid07.storeapp.store.presentation.myCart.MyCartScreenRoot
import com.devdroid07.storeapp.store.presentation.myCart.MyCartViewModel
import com.devdroid07.storeapp.store.presentation.pay.FinishPayScreenRoot
import com.devdroid07.storeapp.store.presentation.pay.FinishPayViewModel
import com.devdroid07.storeapp.store.presentation.payment.PaymentScreenRoot
import com.devdroid07.storeapp.store.presentation.payment.PaymentViewModel
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailRootScreenRoot
import com.devdroid07.storeapp.store.presentation.productDetail.ProductDetailViewModel
import com.devdroid07.storeapp.store.presentation.search.SearchScreenRoot
import com.devdroid07.storeapp.store.presentation.search.SearchViewModel
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressScreenRoot
import com.devdroid07.storeapp.store.presentation.updateAddress.UpdateAddressViewModel

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
                navigateToDetailProduct = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.DetailProduct.createRoute(it)
                    )
                },
                navigateToSearch = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Search.route
                    )
                },
                navBackStackEntry = navBackEntry,
                navigateToCart = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Cart.route
                    )
                }
            )
        }

        composable(
            route = RoutesScreens.Cart.route
        ) { navBackEntry ->

            val viewModel: MyCartViewModel = hiltViewModel()

            MyCartScreenRoot(
                context = context,
                viewModel = viewModel,
                navigateToPay = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Address.route
                    )
                },
                navigateBack = {
                    navController.navigateBack()
                },
            )

        }
        composable(
            route = RoutesScreens.Search.route
        ) { navBackEntry ->

            val viewModel: SearchViewModel = hiltViewModel()

            SearchScreenRoot(
                viewModel = viewModel,
                navigateToDetailProduct = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.DetailProduct.createRoute(it)
                    )
                }
            )

        }

        composable(
            route = RoutesScreens.Address.route
        ) { navBackEntry ->

            val viewModel: AddressViewModel = hiltViewModel()

            AddressScreenRoot(
                context = context,
                viewModel = viewModel,
                navigateToPayment = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.Payment.createRoute(it.toString())
                    )
                },
                onBack = {
                    navController.navigateBack()
                },
                navigateToUpdateAddress = {
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.EditAddress.createRoute(it)
                    )
                }
            )
        }

        composable(
            route = RoutesScreens.EditAddress.route
        ) { navBackEntry ->

            val viewModel: UpdateAddressViewModel = hiltViewModel()
            UpdateAddressScreenRoot(
                context = context,
                viewModel = viewModel,
                onBack = {
                    navController.navigateBack()
                },
                onSuccessUpdate = {
                    navController.navigateToSingleInclusive(
                        navBackEntry,
                        RoutesScreens.Address.route
                    )
                }
            )
        }

        composable(
            route = RoutesScreens.Payment.route
        ) { navBackEntry ->

            val viewModel: PaymentViewModel = hiltViewModel()

            PaymentScreenRoot(
                context = context,
                viewModel = viewModel,
                onBack = {
                    navController.navigateBack()
                },
                navigateToFinishPay = { addressId, cardId, tokenId ->
                    navController.navigateScreen(
                        navBackEntry,
                        RoutesScreens.FinishPay.createRoute(
                            addressId = addressId,
                            cardId = cardId,
                            tokenId = tokenId
                        )
                    )
                }
            )
        }

        composable(
            route = RoutesScreens.FinishPay.route
        ) { navBackEntry ->

            val viewModel: FinishPayViewModel = hiltViewModel()

            FinishPayScreenRoot(
                viewModel = viewModel,
                navigateToHome = {
                    navController.navigateToSingleTop(
                        navBackEntry,
                        RoutesScreens.HomeDrawerRoute.route
                    )
                },
                onBack = {
                    navController.navigateBack()
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

            ProductDetailRootScreenRoot(
                context = context,
                viewModel = viewModel,
                onBack = {
                    navController.navigateBack()
                }
            )

        }
    }
}
