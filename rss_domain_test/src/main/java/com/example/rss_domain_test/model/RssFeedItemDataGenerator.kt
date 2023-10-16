package com.example.rss_domain_test.model

import com.example.rss_domain.model.RssFeedItemData

object RssFeedItemDataGenerator {

    fun rssListItemData(
        description: String = "description",
        title: String = "title",
        url: String = "url"
    ) = RssFeedItemData(
        description = description,
        title = title,
        url = url
    )
}
