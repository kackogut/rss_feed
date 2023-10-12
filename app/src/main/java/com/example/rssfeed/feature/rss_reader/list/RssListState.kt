package com.example.rssfeed.feature.rss_reader.list

import com.example.rssfeed.feature.rss_reader.list.model.RssListItemDisplay
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay

internal sealed class RssListState {
    data object Loading : RssListState()
    data class Data(val list: List<RssListItemDisplay>) : RssListState()
    data class Error(val connectionError: RssParserErrorDisplay) : RssListState()
}
