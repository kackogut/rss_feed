package com.example.rss_domain.mock

import com.example.rss_repository.model.RssItemResponse

object RssItemResponseGenerator {

    fun rssItemResponse(
        description: String = "description response",
        title: String = "title",
        url: String = "url"
    ) = RssItemResponse(
        description = description,
        title = title,
        url = url
    )
}
