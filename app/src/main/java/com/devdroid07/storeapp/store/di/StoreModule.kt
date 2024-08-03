package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.remote.api.CopomexApi
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.repository.AddressRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.StoreRepositoryImpl
import com.devdroid07.storeapp.store.domain.repository.StoreRepository
import com.devdroid07.storeapp.store.domain.usecases.AddFavoriteProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.AddMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.AddReviewProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.CreateAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetAllMyAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetAllProducts
import com.devdroid07.storeapp.store.domain.usecases.GetFavoritesProductsUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetInfoByPostalCodeUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetReviewsProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.GetSingleProduct
import com.devdroid07.storeapp.store.domain.usecases.RemoveProductMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.RemoveProductMyFavoritesUseCase
import com.devdroid07.storeapp.store.domain.usecases.SearchProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.StoreUseCases
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import com.devdroid07.storeapp.store.domain.usecases.address.DeleteAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

    @Provides
    @Singleton
    fun provideStoreRepository(
        api: StoreApiService,
        sessionStorage: SessionStorage,
        copomexApi: CopomexApi,
    ): StoreRepository {
        return StoreRepositoryImpl(
            api = api,
            sessionStorage = sessionStorage,
            copomexApi = copomexApi
        )
    }

    @Singleton
    @Provides
    fun provideAddressRepository(
        storeApiService: StoreApiService,
        copomexApi: CopomexApi,
        sessionStorage: SessionStorage
    ): AddressRepositoryImpl{
        return AddressRepositoryImpl(
            storeApiService = storeApiService,
            copomexApi = copomexApi,
            sessionStorage = sessionStorage
        )
    }


    @Provides
    @Singleton
    fun provideAddressUseCases(
        repository: AddressRepositoryImpl
    ): AddressUseCases{
        return AddressUseCases(
            createAddressUseCase = CreateAddressUseCase(repository),
            getAllMyAddressUseCase = GetAllMyAddressUseCase(repository),
            getInfoByPostalCodeUseCase = GetInfoByPostalCodeUseCase(repository),
            deleteAddressUseCase = DeleteAddressUseCase(repository)
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