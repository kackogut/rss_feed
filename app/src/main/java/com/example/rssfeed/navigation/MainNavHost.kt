package com.example.rssfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rssfeed.feature.input.RssInputRoute

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainNavHost.URL_INPUT_SCREEN) {
        composable(MainNavHost.URL_INPUT_SCREEN) {
            RssInputRoute(
                navigateToRssList = { url ->

                }
            )
        }
    }
}

object MainNavHost {

    const val URL_INPUT_SCREEN = "url_input"
}
