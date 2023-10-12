package com.example.rssfeed.feature.rss_reader.list

import com.example.rssfeed.feature.rss_reader.list.model.RssListItemDisplay

sealed class RssListState {
    data object Loading : RssListState()
    data class Data(val list: List<RssListItemDisplay>) : RssListState()
    data object Error : RssListState()
}
