package com.example.rssfeed.feature.rss_reader.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rssfeed.design.components.BaseToolbar
import com.example.rssfeed.design.components.FullScreenLoading
import com.example.rssfeed.feature.rss_reader.list.components.RssFeedList

@Composable
internal fun RssListRoute(
    rssListViewModel: RssListViewModel = hiltViewModel(),
    rssUrl: String,
    onNavigateBack: () -> Unit
) {
    rssListViewModel.getRssFeed(rssUrl)

    val rssListState by rssListViewModel.state.collectAsState()

    RssListScreen(
        state = rssListState,
        url = rssUrl,
        onBackClick = onNavigateBack
    )
}

@Composable
private fun RssListScreen(
    state: RssListState,
    url: String,
    onBackClick: () -> Unit
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
                is RssListState.Data -> RssFeedList(state.list)
                RssListState.Error -> TODO()
                RssListState.Loading -> FullScreenLoading()
            }
        }
    }
}
