package com.example.rssfeed.feature.rss_reader.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rssfeed.design.components.BaseToolbar
import com.example.rssfeed.design.components.FullScreenLoading
import com.example.rssfeed.feature.rss_reader.list.components.RssFeedList
import com.example.rssfeed.feature.rss_reader.list.components.RssListErrorComponent

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

    RssListScreen(
        state = rssListState,
        url = rssUrl,
        onBackClick = onNavigateBack,
        onTryAgainClick = { rssListViewModel.getRssFeed(rssUrl) },
        onRssItemClick = onRssItemClick
    )
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
                            .padding(horizontal = 24.dp),
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
