package com.example.rssfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rssfeed.feature.rss_reader.input.RssInputRoute
import com.example.rssfeed.feature.rss_reader.list.RssListRoute

const val URL_INPUT_SCREEN = "url_input"

const val RSS_LIST_URL_ARGUMENT = "rss_website"
const val RSS_LIST_SCREEN = "rss_list_input"

fun routeNameArgument(name: String, argument: String) = "$name/{$argument}"

fun routeNavigationArgument(name: String, argument: String) = "$name/$argument"

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = URL_INPUT_SCREEN) {
        composable(URL_INPUT_SCREEN) {
            RssInputRoute(
                navigateToRssList = { url ->
                    navController.navigate(
                        routeNavigationArgument(RSS_LIST_SCREEN, url)
                    )
                }
            )
        }

        composable(
            routeNameArgument(RSS_LIST_SCREEN, RSS_LIST_URL_ARGUMENT),
            arguments = listOf(navArgument(RSS_LIST_URL_ARGUMENT) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            RssListRoute(
                rssUrl = backStackEntry.arguments?.getString(RSS_LIST_URL_ARGUMENT)
                    ?: error("Argument $RSS_LIST_URL_ARGUMENT not passed to $RSS_LIST_SCREEN")
            )
        }
    }
}


