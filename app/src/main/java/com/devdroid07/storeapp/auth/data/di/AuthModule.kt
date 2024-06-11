package com.devdroid07.storeapp.auth.data.di

import com.devdroid07.storeapp.auth.data.matcher.EmailPatternValidator
import com.devdroid07.storeapp.auth.domain.repository.PatternValidator
import com.devdroid07.storeapp.auth.domain.usecases.UserDataValidator
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
    fun providePatternValidator(): PatternValidator{
        return EmailPatternValidator()
    }
    @Provides
    @Singleton
    fun provideUserDataValidator(
        patternValidator: PatternValidator
    ): UserDataValidator{
        return UserDataValidator(
            patternValidator = patternValidator
        )
    }
}