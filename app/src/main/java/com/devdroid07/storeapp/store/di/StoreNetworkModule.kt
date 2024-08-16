package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.BuildConfig
import com.devdroid07.storeapp.core.di.CoreRetrofit
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.network.api.AccountApiService
import com.devdroid07.storeapp.store.data.network.api.AddressApiService
import com.devdroid07.storeapp.store.data.network.api.CardApiService
import com.devdroid07.storeapp.store.data.network.api.CartApiService
import com.devdroid07.storeapp.store.data.network.api.CopomexApi
import com.devdroid07.storeapp.store.data.network.api.FavoriteApiService
import com.devdroid07.storeapp.store.data.network.api.MercadoPagoApiService
import com.devdroid07.storeapp.store.data.network.api.OrderApiService
import com.devdroid07.storeapp.store.data.network.api.PaymentApiService
import com.devdroid07.storeapp.store.data.network.api.ProductApiService
import com.devdroid07.storeapp.store.data.network.interceptor.StoreApiInterceptor
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
    @StoreRetrofit
    fun provideStoreRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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
    @Singleton
    fun provideMercadoPagoApiService(@CoreRetrofit retrofit: Retrofit): MercadoPagoApiService {
        return retrofit.newBuilder().baseUrl(BuildConfig.MERCADOPAGO_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(MercadoPagoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApiService(@StoreRetrofit retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiService(@StoreRetrofit retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteApiService(@StoreRetrofit retrofit: Retrofit): FavoriteApiService {
        return retrofit.create(FavoriteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressApiService(@StoreRetrofit retrofit: Retrofit): AddressApiService {
        return retrofit.create(AddressApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCardApiService(@StoreRetrofit retrofit: Retrofit): CardApiService {
        return retrofit.create(CardApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentApiService(@StoreRetrofit retrofit: Retrofit): PaymentApiService {
        return retrofit.create(PaymentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderApiService(@StoreRetrofit retrofit: Retrofit): OrderApiService{
        return retrofit.create(OrderApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        @StoreRetrofit retrofit: Retrofit,
    ): AccountApiService {
        return retrofit.create(AccountApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCopomexApi(
        @CoreRetrofit retrofit: Retrofit
    ): CopomexApi {
        return retrofit.newBuilder().baseUrl(BuildConfig.COPOMEX_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CopomexApi::class.java)
    }

}