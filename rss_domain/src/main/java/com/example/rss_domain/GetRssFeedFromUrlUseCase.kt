package com.example.rss_domain

import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.toDomainModel
import com.example.rss_repository.RssRepository
import javax.inject.Inject

class GetRssFeedFromUrlUseCase @Inject constructor(
    private val rssRepository: RssRepository
) {
    suspend fun execute(url: String): Result<List<RssFeedItemData>> = runCatching {
        rssRepository.parseRssUrl(url)
            .map { it.toDomainModel() }
    }
}
