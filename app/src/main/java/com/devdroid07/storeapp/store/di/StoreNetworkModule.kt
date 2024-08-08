package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.BuildConfig
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.remote.api.CopomexApi
import com.devdroid07.storeapp.store.data.remote.api.MercadoPagoApiService
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.remote.interceptor.StoreApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreNetworkModule {

    @Provides
    @Singleton
    fun provideStoreApiInterceptor(
        sessionStorage: SessionStorage,
    ): StoreApiInterceptor {
        return StoreApiInterceptor(sessionStorage)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        storeApiInterceptor: StoreApiInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(storeApiInterceptor)
            .build()
    }

    @Provides
    fun provideMercadoPagoApiService(
        retrofit: Retrofit,
    ): MercadoPagoApiService {
        return retrofit.newBuilder().baseUrl("https://api.mercadopago.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(MercadoPagoApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideStoreApiService(
        retrofit: Retrofit,
        okHttpClient: OkHttpClient,
    ): StoreApiService {
        return retrofit.newBuilder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(StoreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCopomexApi(): CopomexApi {
        return Retrofit.Builder().baseUrl("https://api.copomex.com/query/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CopomexApi::class.java)
    }

}