package com.example.rss_domain

import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.toDomainModel
import com.example.rss_repository.RssRepository
import com.example.rss_repository.model.RssParserErrorResponse
import javax.inject.Inject

class SubscribeToRssFeedFromUrlUseCase @Inject constructor(
    private val rssRepository: RssRepository
) {
    suspend fun execute(url: String): Result<List<RssFeedItemData>> = runCatching {
        try {
            rssRepository.parseRssUrl(url)
                .map { it.toDomainModel() }
        } catch (exception: RssParserErrorResponse) {
            throw exception.toDomainModel()
        }
    }
}
