package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.remote.api.CopomexApi
import com.devdroid07.storeapp.store.data.remote.api.MercadoPagoApiService
import com.devdroid07.storeapp.store.data.remote.api.StoreApiService
import com.devdroid07.storeapp.store.data.repository.AddressRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.CardRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.PaymentRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.StoreRepositoryImpl
import com.devdroid07.storeapp.store.domain.repository.CardRepository
import com.devdroid07.storeapp.store.domain.repository.PaymentRepository
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
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import com.devdroid07.storeapp.store.domain.usecases.address.CreateAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.DeleteAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetAllMyAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetInfoByPostalCodeUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.CardUseCases
import com.devdroid07.storeapp.store.domain.usecases.card.CreateCardTokenUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.CreateCardUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.GetAllMyCardsUseCase
import com.devdroid07.storeapp.store.domain.usecases.payment.CreatePaymentAndOrderUseCase
import com.devdroid07.storeapp.store.domain.usecases.payment.PaymentUseCases
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
        sessionStorage: SessionStorage,
    ): AddressRepositoryImpl {
        return AddressRepositoryImpl(
            storeApiService = storeApiService,
            copomexApi = copomexApi,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideCardRepository(
        storeApiService: StoreApiService,
        sessionStorage: SessionStorage,
        mercadoPagoApiService: MercadoPagoApiService,
    ): CardRepository {
        return CardRepositoryImpl(
            sessionStorage = sessionStorage,
            storeApiService = storeApiService,
            mercadoPagoApiService = mercadoPagoApiService
        )
    }

    @Singleton
    @Provides
    fun providePaymentRepository(
        storeApiService: StoreApiService,
        sessionStorage: SessionStorage,
    ): PaymentRepository {
        return PaymentRepositoryImpl(
            sessionStorage,
            storeApiService
        )
    }

    @Singleton
    @Provides
    fun providePaymentUseCases(
        repository: PaymentRepository,
    ): PaymentUseCases {
        return PaymentUseCases(createPaymentAndOrderUseCase = CreatePaymentAndOrderUseCase(repository))
    }

    @Singleton
    @Provides
    fun provideCardUseCases(
        repository: CardRepository,
    ): CardUseCases {
        return CardUseCases(
            getAllMyCardsUseCase = GetAllMyCardsUseCase(repository),
            createCardUseCase = CreateCardUseCase(repository),
            createCardTokenUseCase = CreateCardTokenUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideAddressUseCases(
        repository: AddressRepositoryImpl,
    ): AddressUseCases {
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
        repository: StoreRepository,
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