package com.devdroid07.storeapp.store.di

import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.store.data.network.api.AddressApiService
import com.devdroid07.storeapp.store.data.network.api.CardApiService
import com.devdroid07.storeapp.store.data.network.api.CartApiService
import com.devdroid07.storeapp.store.data.network.api.CopomexApi
import com.devdroid07.storeapp.store.data.network.api.FavoriteApiService
import com.devdroid07.storeapp.store.data.network.api.MercadoPagoApiService
import com.devdroid07.storeapp.store.data.network.api.PaymentApiService
import com.devdroid07.storeapp.store.data.network.api.ProductApiService
import com.devdroid07.storeapp.store.data.repository.AddressRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.CardRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.CartRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.FavoriteRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.PaymentRepositoryImpl
import com.devdroid07.storeapp.store.data.repository.ProductRepositoryImpl
import com.devdroid07.storeapp.store.domain.repository.CardRepository
import com.devdroid07.storeapp.store.domain.repository.CartRepository
import com.devdroid07.storeapp.store.domain.repository.FavoriteRepository
import com.devdroid07.storeapp.store.domain.repository.PaymentRepository
import com.devdroid07.storeapp.store.domain.repository.ProductRepository
import com.devdroid07.storeapp.store.domain.usecases.cart.AddMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.cart.GetMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.AddressUseCases
import com.devdroid07.storeapp.store.domain.usecases.address.CreateAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.DeleteAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetAllMyAddressUseCase
import com.devdroid07.storeapp.store.domain.usecases.address.GetInfoByPostalCodeUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.CardUseCases
import com.devdroid07.storeapp.store.domain.usecases.card.CreateCardTokenUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.CreateCardUseCase
import com.devdroid07.storeapp.store.domain.usecases.card.GetAllMyCardsUseCase
import com.devdroid07.storeapp.store.domain.usecases.cart.CartUseCases
import com.devdroid07.storeapp.store.domain.usecases.payment.CreatePaymentAndOrderUseCase
import com.devdroid07.storeapp.store.domain.usecases.payment.PaymentUseCases
import com.devdroid07.storeapp.store.domain.usecases.favorite.AddFavoriteProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.product.AddReviewProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.product.GetAllBannersUseCases
import com.devdroid07.storeapp.store.domain.usecases.product.GetAllProducts
import com.devdroid07.storeapp.store.domain.usecases.favorite.GetFavoritesProductsUseCase
import com.devdroid07.storeapp.store.domain.usecases.product.GetReviewsProductUseCase
import com.devdroid07.storeapp.store.domain.usecases.product.GetSingleProduct
import com.devdroid07.storeapp.store.domain.usecases.product.ProductUseCases
import com.devdroid07.storeapp.store.domain.usecases.cart.RemoveProductMyCartUseCase
import com.devdroid07.storeapp.store.domain.usecases.favorite.FavoriteUseCases
import com.devdroid07.storeapp.store.domain.usecases.favorite.RemoveProductMyFavoritesUseCase
import com.devdroid07.storeapp.store.domain.usecases.product.SearchProductUseCase
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
    fun provideProductRepository(
        api: ProductApiService,
        sessionStorage: SessionStorage,
    ): ProductRepository {
        return ProductRepositoryImpl(
            productApiService = api,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideCartRepository(
        cartApiService: CartApiService,
        sessionStorage: SessionStorage,
    ): CartRepository {
        return CartRepositoryImpl(
            cartApiService,
            sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        favoriteApiService: FavoriteApiService,
        sessionStorage: SessionStorage,
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoriteApiService,
            sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideAddressRepository(
        addressApiService: AddressApiService,
        copomexApi: CopomexApi,
        sessionStorage: SessionStorage,
    ): AddressRepositoryImpl {
        return AddressRepositoryImpl(
            addressApiService = addressApiService,
            copomexApi = copomexApi,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideCardRepository(
        cardApiService: CardApiService,
        sessionStorage: SessionStorage,
        mercadoPagoApiService: MercadoPagoApiService,
    ): CardRepository {
        return CardRepositoryImpl(
            sessionStorage = sessionStorage,
            cardApiService = cardApiService,
            mercadoPagoApiService = mercadoPagoApiService
        )
    }

    @Singleton
    @Provides
    fun providePaymentRepository(
        paymentApiService: PaymentApiService,
        sessionStorage: SessionStorage,
    ): PaymentRepository {
        return PaymentRepositoryImpl(
            sessionStorage,
            paymentApiService
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

    @Singleton
    @Provides
    fun provideCartUseCases(
        repository: CartRepository
    ): CartUseCases{
        return CartUseCases(
            getMyCartUseCase = GetMyCartUseCase(repository),
            addMyCartUseCase = AddMyCartUseCase(repository),
            removeProductMyCartUseCase = RemoveProductMyCartUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteUseCases(
        repository: FavoriteRepository
    ): FavoriteUseCases{
        return FavoriteUseCases(
            removeFavoriteProductUseCase = RemoveProductMyFavoritesUseCase(repository),
            addFavoriteProductUseCase = AddFavoriteProductUseCase(repository),
            getFavoritesProductsUseCase = GetFavoritesProductsUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideProductUseCase(
        repository: ProductRepository,
    ): ProductUseCases {
        return ProductUseCases(
            getAllProducts = GetAllProducts(repository),
            getSingleProduct = GetSingleProduct(repository),
            addReviewProductUseCase = AddReviewProductUseCase(repository),
            getReviewsProductUseCase = GetReviewsProductUseCase(repository),
            searchProductUseCase = SearchProductUseCase(repository),
            getAllBannersUseCases = GetAllBannersUseCases(repository)
        )
    }

}