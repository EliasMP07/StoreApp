package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.store.data.remote.FakeStoreApi
import com.devdroid07.storeapp.store.data.repository.StoreRepositoryImpl
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import com.devdroid07.storeapp.store.domain.usecases.GetAllProducts
import com.devdroid07.storeapp.store.domain.usecases.GetSingleProduct
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {


    @Provides
    @Singleton
    fun provideFakeStoreApi(): FakeStoreApi{
        return Retrofit.Builder()
            .baseUrl(FakeStoreApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FakeStoreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(
        api: FakeStoreApi
    ): StoreRepository {
        return StoreRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideStoreUseCase(
        repository: StoreRepository
    ): StoreUseCases{
        return StoreUseCases(
            getAllProducts = GetAllProducts(repository),
            getSingleProduct = GetSingleProduct(repository)
        )
    }
}