package com.devdroid07.storeapp.store.data.remote.interceptor

import com.devdroid07.storeapp.core.domain.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class StoreApiInterceptor (
    private val sessionStorage: SessionStorage
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder()
        val token = runBlocking {
            sessionStorage.get()?.token.orEmpty()
        }
        currentRequest.addHeader("Authorization",token)
        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}