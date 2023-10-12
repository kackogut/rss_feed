package com.example.rss_domain.model

import com.example.rss_repository.model.RssItemResponse

internal fun RssItemResponse.toDomainModel() = RssFeedItemData(
    description = description,
    url = url,
    title = title
)
