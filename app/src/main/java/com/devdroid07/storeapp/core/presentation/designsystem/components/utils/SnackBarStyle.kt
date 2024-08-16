package com.devdroid07.storeapp.core.presentation.designsystem.components.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Update
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.devdroid07.storeapp.core.presentation.designsystem.errorLight
import com.devdroid07.storeapp.core.presentation.designsystem.primaryLight
import com.devdroid07.storeapp.core.presentation.designsystem.successColor

/**
 * Clase sellada que contiene los tipos de estilos de los snackbar
 * dependiendo la respuesta
 *
 * @param icon Icono que se mostrara en el snackbar este puede ser opcional
 * @param backgroundColor Color del snackbar
 * @param type El tipo de respuesta
 * @param duration Duracion del snankbar en pantalla
 */
sealed class SnackBarStyle(
    val icon: ImageVector?,
    val backgroundColor: Color,
    val type: String,
    val duration: Int
) {
    data object SuccessRemoveCart : SnackBarStyle(
        icon = Icons.Default.RemoveShoppingCart,
        backgroundColor = primaryLight,
        type = "SUCCESS_REMOVE_CART",
        duration = 2
    )

    data object Success : SnackBarStyle(
        icon = Icons.Default.Check,
        backgroundColor = successColor,
        type = "SUCCESS",
        duration = 2
    )

    data object SuccessAddCart : SnackBarStyle(
        icon = Icons.Default.ShoppingCart,
        backgroundColor = successColor,
        type = "SUCCESS_ADD_CART",
        duration = 3
    )

    data object SuccessUpdateQuantity : SnackBarStyle(
        icon = Icons.Default.Update,
        backgroundColor = successColor,
        type = "SUCCESS_UPDATE_QUANTITY",
        duration = 2
    )

    data object SuccessAddFavorite : SnackBarStyle(
        icon = Icons.Default.Favorite,
        backgroundColor = successColor,
        type = "SUCCESS_ADD_FAV",
        duration = 2
    )

    data object SuccessRemoveFavorite : SnackBarStyle(
        icon = Icons.Default.FavoriteBorder,
        backgroundColor = primaryLight,
        type = "SUCCESS_REMOVE_FAVORITE",
        duration = 2
    )

    data object SuccessAddReview : SnackBarStyle(
        icon = Icons.Default.RateReview,
        backgroundColor = successColor,
        type = "SUCCESS_ADD_REVIEW",
        duration = 2
    )

    data object Error : SnackBarStyle(
        icon = Icons.Default.Error,
        backgroundColor = errorLight,
        type = "ERROR",
        duration = 2
    )

    companion object{
        fun TypeStyle(value: String): SnackBarStyle{
            return when(value){
                SuccessRemoveCart.type -> SuccessRemoveCart
                SuccessAddCart.type -> SuccessAddCart
                SuccessAddFavorite.type -> SuccessAddFavorite
                SuccessRemoveFavorite.type -> SuccessRemoveFavorite
                SuccessAddReview.type -> SuccessAddReview
                SuccessUpdateQuantity.type -> SuccessUpdateQuantity
                else -> Error
            }
        }
    }
}