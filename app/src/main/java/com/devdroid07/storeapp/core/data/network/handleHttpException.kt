package com.devdroid07.storeapp.core.data.network

import com.devdroid07.storeapp.core.domain.util.DataError
import com.devdroid07.storeapp.core.domain.util.Result
import retrofit2.HttpException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

fun <T> handleHttpException(e: HttpException): Result<T, DataError.Network> {
    return when(e.code()){
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

suspend inline fun <T> safeCall(crossinline apiCall: suspend () -> T): Result<T, DataError.Network> {
    return try {
        val result = apiCall()
        Result.Success(result)
    } catch (e: HttpException) {
        handleHttpException(e)
    }catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    }  catch(e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
}
