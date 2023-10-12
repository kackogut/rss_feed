package com.example.rssfeed.feature.rss_reader.list.model

import com.example.rss_domain.model.RssFeedItemData

internal fun RssFeedItemData.toDisplay(id: Int) = RssListItemDisplay(
    id = id,
    title = title,
    description = description,
    url = url
)
