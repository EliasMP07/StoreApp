package com.devdroid07.storeapp.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

/**
 * Composable para cargar las imagenes de Coil y tener un crossfade de animacion
 *
 * @param modifier Modificador para el composable para cambiar su diseño
 * @param model Imagen que se va cargar
 * @param contentScale Escalado de la imagen por default Fit
 * @param durationCrossfade Duracion de la animacion del crossfade por default 500
 * @param contentLoading Composable cuando la imagen este cargando
 */
@Composable
fun LoadImageCoil(
    modifier: Modifier = Modifier,
    model: Any?,
    @StringRes contentDescription: Int,
    contentScale: ContentScale = ContentScale.Fit,
    durationCrossfade: Int = 500,
    contentLoading: @Composable () -> Unit,
){
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current).data(model).crossfade(true).crossfade(durationCrossfade)
            .build(),
        contentScale = contentScale,
        contentDescription = stringResource(id = contentDescription),
        loading = {
            contentLoading()
        },
        error = {
            ErrorImageLoad()
        }
    )
}