package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.remote.StoreApiService
import com.devdroid07.storeapp.store.data.repository.StoreRepositoryImpl
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import com.devdroid07.storeapp.store.domain.usecases.AddFavoriteProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.AddMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.AddReviewProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetAllProducts
import com.devdroid07.storeapp.store.domain.usecases.GetFavoritesProductsUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetReviewsProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetSingleProduct
import com.devdroid07.storeapp.store.domain.usecases.RemoveProductMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.RemoveProductMyFavoritesUseCase
import com.devdroid07.storeapp.store.domain.usecases.SearchProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {


    @Provides
    @Singleton
    fun provideStoreApiService(
        retrofit: Retrofit
    ): StoreApiService {
        return retrofit.create(StoreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(
        api: StoreApiService,
        sessionStorage: SessionStorage
    ): StoreRepository {
        return StoreRepositoryImpl(
            api = api,
            sessionStorage = sessionStorage
        )
    }

    @Provides
    @Singleton
    fun provideStoreUseCase(
        repository: StoreRepository
    ): StoreUseCases {
        return StoreUseCases(
            getAllProducts = GetAllProducts(repository),
            getSingleProduct = GetSingleProduct(repository),
            addReviewProductUseCase = AddReviewProductUseCase(repository),
            getReviewsProductUseCase = GetReviewsProductUseCase(repository),
            searchProductUseCase = SearchProductUseCase(repository),
            getMyCartUseCase = GetMyCartUseCase(repository),
            addMyCartUseCase = AddMyCartUseCase(repository),
            removeProductMyCartUseCase = RemoveProductMyCartUseCase(repository),
            removeFavoriteProductUseCase = RemoveProductMyFavoritesUseCase(repository),
            addFavoriteProductUseCase = AddFavoriteProductUseCase(repository),
            getFavoritesProductsUseCase = GetFavoritesProductsUseCase(repository)
        )
    }

}