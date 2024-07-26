package com.devdroid07.storeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.navigation.NavigationRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value.isCheckingAuth
            }
        }
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            StoreAppTheme {
                if (!state.isCheckingAuth){
                    val  navController = rememberNavController()
                    NavigationRoot(navController = navController, isLoggedIn = state.isLoggedIn, context = this)
                }
            }
        }
    }
}

