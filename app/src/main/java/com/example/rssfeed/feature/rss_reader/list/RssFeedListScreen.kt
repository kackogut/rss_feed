package com.example.rssfeed.feature.rss_reader.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rssfeed.design.components.BaseToolbar
import com.example.rssfeed.design.components.FullScreenLoading
import com.example.rssfeed.feature.rss_reader.list.components.RssFeedList
import com.example.rssfeed.feature.rss_reader.list.components.RssListErrorComponent
import com.example.rssfeed.feature.rss_reader.list.model.RssListItemDisplay
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay
import com.example.rssfeed.ui.theme.RssFeedTheme
import com.example.rssfeed.ui.utils.Spacings

@Composable
internal fun RssListRoute(
    rssListViewModel: RssListViewModel = hiltViewModel(),
    rssUrl: String,
    onNavigateBack: () -> Unit,
    onRssItemClick: (String) -> Unit
) {
    LaunchedEffect(rssUrl) {
        rssListViewModel.getRssFeed(rssUrl)
    }

    val rssListState by rssListViewModel.state.collectAsState()

    RssFeedTheme {
        RssListScreen(
            state = rssListState,
            url = rssUrl,
            onBackClick = onNavigateBack,
            onTryAgainClick = { rssListViewModel.getRssFeed(rssUrl) },
            onRssItemClick = onRssItemClick
        )
    }
}

@Composable
private fun RssListScreen(
    state: RssListState,
    url: String,
    onBackClick: () -> Unit,
    onTryAgainClick: () -> Unit,
    onRssItemClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            BaseToolbar(
                title = url,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (state) {
                is RssListState.Data -> {
                    RssFeedList(list = state.list, onClick = onRssItemClick)
                }

                is RssListState.Error -> {
                    RssListErrorComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Spacings.large),
                        errorDisplay = state.connectionError,
                        onTryAgainClick = onTryAgainClick
                    )
                }

                RssListState.Loading -> {
                    FullScreenLoading()
                }
            }
        }
    }
}

@Composable
@Preview
private fun LoadingRssInputScreenPreview() {
    RssFeedTheme {
        RssListScreen(
            state = RssListState.Loading,
            url = "https://url.com",
            onBackClick = {},
            onTryAgainClick = {},
            onRssItemClick = {}
        )
    }
}

@Composable
@Preview
private fun RssListDataScreenPreview() {
    RssFeedTheme {
        RssListScreen(
            state = RssListState.Data(
                list = listOf(
                    RssListItemDisplay(
                        id = 0,
                        title = "title",
                        description = "description very long, lorem ipsum",
                        url = "https://url.com/feed1"
                    )
                )
            ),
            url = "https://url.com",
            onBackClick = {},
            onTryAgainClick = {},
            onRssItemClick = {}
        )
    }
}

@Composable
@Preview
private fun RssErrorDataScreenPreview() {
    RssFeedTheme {
        RssListScreen(
            state = RssListState.Error(connectionError = RssParserErrorDisplay.ConnectionError),
            url = "https://url.com",
            onBackClick = {},
            onTryAgainClick = {},
            onRssItemClick = {}
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RssListDataScreenDarkModelPreview() {
    RssFeedTheme {
        RssListScreen(
            state = RssListState.Data(
                list = listOf(
                    RssListItemDisplay(
                        id = 0,
                        title = "title",
                        description = "description very long, lorem ipsum",
                        url = "https://url.com/feed1"
                    )
                )
            ),
            url = "https://url.com",
            onBackClick = {},
            onTryAgainClick = {},
            onRssItemClick = {}
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RssErrorScreenDarkModelPreview() {
    RssFeedTheme {
        RssListScreen(
            state = RssListState.Error(connectionError = RssParserErrorDisplay.ParsingError),
            url = "https://url.com",
            onBackClick = {},
            onTryAgainClick = {},
            onRssItemClick = {}
        )
    }
}
