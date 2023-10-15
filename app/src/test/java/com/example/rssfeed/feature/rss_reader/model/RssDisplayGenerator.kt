package com.example.rssfeed.feature.rss_reader.model

import com.example.rssfeed.feature.rss_reader.list.model.RssListItemDisplay

internal object RssDisplayGenerator {

    fun rssListItemDisplay(
        id: Int = 9,
        description: String = "display description",
        title: String = "display title",
        url: String = "display utl"
    ) = RssListItemDisplay(
        id = id,
        description = description,
        title = title,
        url = url
    )
}
