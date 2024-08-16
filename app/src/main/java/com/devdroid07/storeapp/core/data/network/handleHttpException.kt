package com.devdroid07.storeapp.core.data.network

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Realiza una llamada de red segura, manejando varias excepciones comunes y retornando un resultado de tipo Result.
 *
 * @param execute Una función lambda que ejecuta la llamada de red y retorna un "Response".
 * @return Un "Result" que puede ser un éxito con el cuerpo de la respuesta, un error de red o una exepcion.
 */
inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, DataError.Network> {

    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: IOException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)

}

/**
 * Convierte un objeto "Response" en un "Result", evaluando el código de estado HTTP.
 *
 * @param response El objeto "Response" de la llamada de red de retrofit.
 * @return Un "Result" que puede ser un éxito con el cuerpo de la respuesta o un error basado en el código de estado de la respuesta del backend.
 */
inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when (response.code()) {
        in 200..299 -> Result.Success(response.body()!!)
        400 -> Result.Error(DataError.Network.BAD_REQUEST)
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}