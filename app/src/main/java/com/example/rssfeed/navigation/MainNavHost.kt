package com.example.rssfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rssfeed.feature.rss_reader.input.RssInputRoute
import com.example.rssfeed.feature.rss_reader.list.RssListRoute
import com.example.rssfeed.feature.rss_reader.webview.RssDetailsWebViewRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val URL_INPUT_SCREEN = "url_input"

const val RSS_LIST_URL_ARGUMENT = "rss_website"
const val RSS_LIST_SCREEN = "rss_list_input"

const val RSS_DETAILS_ARGUMENT = "rss_details_website"
const val RSS_DETAILS_WEB_VIEW = "rss_details_web_view"

private fun routeNameArgument(name: String, argument: String) = "$name/{$argument}"

private fun routeNavigationArgument(name: String, argument: String) = "$name/$argument"

private fun encodeUrlParameter(url: String) =
    URLEncoder.encode(url, StandardCharsets.UTF_8.toString())

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = URL_INPUT_SCREEN) {
        composable(URL_INPUT_SCREEN) {
            RssInputRoute(
                navigateToRssList = { url ->
                    navController.navigate(
                        routeNavigationArgument(RSS_LIST_SCREEN, encodeUrlParameter(url))
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
                    ?: error("Argument $RSS_LIST_URL_ARGUMENT not passed to $RSS_LIST_SCREEN"),
                onNavigateBack = navController::popBackStack,
                onRssItemClick = { url ->
                    navController.navigate(
                        routeNavigationArgument(RSS_DETAILS_WEB_VIEW, encodeUrlParameter(url))
                    )
                }
            )
        }

        composable(
            routeNameArgument(RSS_DETAILS_WEB_VIEW, RSS_DETAILS_ARGUMENT),
            arguments = listOf(navArgument(RSS_DETAILS_ARGUMENT) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            RssDetailsWebViewRoute(
                url = backStackEntry.arguments?.getString(RSS_DETAILS_ARGUMENT)
                    ?: error("Argument $RSS_DETAILS_ARGUMENT not passed to $RSS_DETAILS_WEB_VIEW"),
                onNavigateBack = navController::popBackStack
            )
        }
    }
}
