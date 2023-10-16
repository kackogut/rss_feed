package com.example.rss_domain.model

import com.example.rss_repository.model.RssItemResponse

object RssFeedItemDataMapperValidator {

    fun expectedMapFromResponseModel(
        response: RssItemResponse
    ) = with(response) {
        RssFeedItemData(
            description = description,
            title = title,
            url = url
        )
    }
}
