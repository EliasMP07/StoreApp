package com.devdroid07.storeapp.auth.data.di

import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import com.devdroid07.storeapp.auth.domain.usecases.AuthUseCases
import com.devdroid07.storeapp.auth.domain.usecases.LoginWithEmailAndPasswordUseCase
import com.devdroid07.storeapp.auth.domain.usecases.SignUpWithEmailUseCase
import com.devdroid07.storeapp.auth.domain.validator.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideUserDataValidator(): UserDataValidator {
        return UserDataValidator()
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            loginWithEmailAndPasswordUseCase = LoginWithEmailAndPasswordUseCase(repository),
            signUpWithEmailUseCase = SignUpWithEmailUseCase(repository)
        )
    }
}