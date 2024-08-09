package com.devdroid07.storeapp.store.presentation.home.navigationDrawer

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devdroid07.storeapp.navigation.util.RoutesScreens
import com.devdroid07.storeapp.navigation.util.navigateBack
import com.devdroid07.storeapp.navigation.util.navigateToSingleTop
import com.devdroid07.storeapp.store.presentation.AccountViewModel
import com.devdroid07.storeapp.store.presentation.account.AccountScreenRoot
import com.devdroid07.storeapp.store.presentation.favorite.FavoriteScreenRoot
import com.devdroid07.storeapp.store.presentation.favorite.FavoriteViewModel
import com.devdroid07.storeapp.store.presentation.home.HomeScreenRoot
import com.devdroid07.storeapp.store.presentation.home.HomeViewModel
import com.devdroid07.storeapp.store.presentation.home.componets.DrawerContent
import com.devdroid07.storeapp.store.presentation.home.navigationDrawer.utils.drawerItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun HomeDrawerScreens(
    context: Context,
    navController: NavHostController = rememberNavController(),
    navBackStackEntry: NavBackStackEntry,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToDetailProduct: (String) -> Unit,
    navigateSearch: () -> Unit,
    navigateMyCart: () -> Unit
) {

    var currentDrawerRoute by remember { mutableStateOf<RoutesScreens>(RoutesScreens.Home) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    drawerItems = drawerItems,
                    currentRoute = currentDrawerRoute,
                ) { route ->
                    currentDrawerRoute = route
                    when (route) {
                        RoutesScreens.Account -> navController.navigateToSingleTop(navBackStackEntry, route)
                        RoutesScreens.Home -> navController.navigateToSingleTop(navBackStackEntry, route)
                        RoutesScreens.Search -> navigateSearch()
                        RoutesScreens.Favorite -> navController.navigateToSingleTop(navBackStackEntry, route)
                        else -> Unit
                    }
                    coroutineScope.launch { drawerState.close() }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = RoutesScreens.Home.route
        ) {
            composable(
                route = RoutesScreens.Home.route
            ) {

                val viewModel: HomeViewModel = hiltViewModel()

                HomeScreenRoot(
                    viewModel = viewModel,
                    navigateToDetailProduct = navigateToDetailProduct,
                    navigateToAccount = {
                        navController.navigateToSingleTop(navBackStackEntry, RoutesScreens.Account)
                    },
                    navigateToSearch = navigateSearch,
                    navigateToCart = navigateMyCart,
                    openDrawer = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )

            }

            composable(
                route = RoutesScreens.Account.route
            ) {

                val viewModel: AccountViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                AccountScreenRoot(
                    state = state,
                    openDrawer = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }

            composable(
                route = RoutesScreens.Favorite.route
            ) {

                val viewModel: FavoriteViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val onAction = viewModel::onAction

                FavoriteScreenRoot(
                    context = context,
                    state = state,
                    navigateBack = {
                        navController.navigateBack()
                    },
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    navigateDetailProduct = {
                        navigateToDetailProduct(it)
                    },
                    onAction = onAction,
                    viewModel = viewModel
                )

            }

        }
    }
}