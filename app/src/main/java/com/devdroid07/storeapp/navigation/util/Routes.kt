package com.devdroid07.storeapp.navigation.util

sealed class RoutesScreens(val route: String){

    data object Auth: RoutesScreens("auth")
    data object Login: RoutesScreens("login")
    data object Register: RoutesScreens("register")
    data object Intro: RoutesScreens("intro")

    data object Store: RoutesScreens("store")
    data object Cart: RoutesScreens("cart")
    data object Account: RoutesScreens("account")
    data object Payment: RoutesScreens("payment/{${NavArgs.AddressID.key}}"){
        fun createRoute(addressId: String) = "payment/$addressId"
    }
    data object FinishPay: RoutesScreens("finish_payment/{${NavArgs.AddressID.key}}/{${NavArgs.CardID.key}}/{${NavArgs.TokenId.key}}"){
        fun createRoute(addressId: String, cardId: String, tokenId: String) = "finish_payment/$addressId/$cardId/$tokenId"
    }
    data object Favorite: RoutesScreens("favorite")
    data object Settings: RoutesScreens("settings")
    data object Address: RoutesScreens("address")

    data object DetailOrder: RoutesScreens("detail_order/{${NavArgs.OrderId.key}}"){
        fun createRoute(orderId: String) = "detail_order/${orderId}"
    }
    data object EditAddress: RoutesScreens("edit_address/{${NavArgs.AddressID.key}}"){
        fun createRoute(addressId: String) = "edit_address/$addressId"
    }
    data object Orders: RoutesScreens("orders")
    data object Home: RoutesScreens("home")
    data object HomeDrawerRoute: RoutesScreens("home_drawer")
    data object Search: RoutesScreens("search")
    data  object DetailProduct: RoutesScreens("product_detail/{${NavArgs.ProductID.key}}"){
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}

enum class NavArgs(val key: String){
    AddressID("addressId"),
    ProductID("productId"),
    CardID("cardId"),
    TokenId("tokenId"),
    OrderId("orderId")
}