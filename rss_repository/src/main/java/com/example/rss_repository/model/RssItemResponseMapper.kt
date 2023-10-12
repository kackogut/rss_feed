package com.example.rss_repository.model

import com.example.rss_reader.model.ParsedRssItem

internal fun ParsedRssItem.toResponse() = RssItemResponse(
    description = description,
    title = title,
    url = link
)
