package com.devdroid07.storeapp.navigation.util

sealed class RoutesScreens(val route: String){
    data object Auth: RoutesScreens("auth")
    data object Login: RoutesScreens("login")
    data object Register: RoutesScreens("register")
    data object Intro: RoutesScreens("intro")
    data object Store: RoutesScreens("store")
    data object Cart: RoutesScreens("cart")

    data object Favorite: RoutesScreens("favorite")
    data object Settings: RoutesScreens("settings")

    data object Home: RoutesScreens("home")
    data object HomeDrawerRoute: RoutesScreens("home_drawer")
    data object Search: RoutesScreens("search")
    data  object DetailProduct: RoutesScreens("product_detail/{${NavArgs.ProductID.key}}"){
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}

enum class NavArgs(val key: String){
    ProductID("productId")
}