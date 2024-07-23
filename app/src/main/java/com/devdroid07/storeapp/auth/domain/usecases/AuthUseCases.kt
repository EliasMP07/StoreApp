package com.devdroid07.storeapp.auth.domain.usecases

data class AuthUseCases(
    val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase,
    val signUpWithEmailUseCase: SignUpWithEmailUseCase
)
