package com.example.rssfeed.feature.rss_reader.model

import com.example.rss_domain.model.RssFeedItemData

internal object RssDisplayMapperUtils {

    fun expectedListItemFromDomainModel(
        domainModel: RssFeedItemData,
        id: Int
    ) = with(domainModel) {
        RssDisplayGenerator.rssListItemDisplay(
            id = id,
            title = title,
            url = url,
            description = description
        )
    }
}
