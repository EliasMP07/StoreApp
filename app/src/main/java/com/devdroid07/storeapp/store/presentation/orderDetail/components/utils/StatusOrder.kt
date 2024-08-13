package com.devdroid07.storeapp.store.presentation.orderDetail.components.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devdroid07.storeapp.R

sealed class StatusOrder(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    data object Pendiente : StatusOrder(
        R.drawable.img_time,
        R.string.title_pending,
        R.string.description_status_order_peding
    )

    data object Despachando : StatusOrder(
        R.drawable.img_dispatched,
        R.string.title_status_order_dispatched,
        R.string.description_status_order_dispatched
    )

    data object Encamino : StatusOrder(
        R.drawable.img_on_the_way,
        R.string.title_status_order_on_the_way,
        R.string.description_status_order_on_the_way
    )

    data object Entregado : StatusOrder(
        R.drawable.img_delivered,
        R.string.title_status_order_delivered,
        R.string.description_status_order_delivered
    )

    companion object {
        fun fromString(status: String): StatusOrder = when (status) {
            "PAGADO" -> Pendiente
            "DESPACHADO" -> Despachando
            "ENCAMINO" -> Encamino
            "ENTREGADO" -> Entregado
            else -> Pendiente
        }
    }
}