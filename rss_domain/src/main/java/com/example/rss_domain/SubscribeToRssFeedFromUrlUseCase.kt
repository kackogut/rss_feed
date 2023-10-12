package com.example.rss_domain

import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.toDomainModel
import com.example.rss_repository.RssRepository
import com.example.rss_repository.model.RssParserErrorResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscribeToRssFeedFromUrlUseCase @Inject constructor(
    private val rssRepository: RssRepository
) {
    suspend fun execute(url: String): Flow<List<RssFeedItemData>> = flow {
        try {
            emit(rssRepository.parseRssUrl(url).map { it.toDomainModel() })
        } catch (exception: RssParserErrorResponse) {
            throw exception.toDomainModel()
        }

        delay(REFRESH_DELAY_IN_MILLISECONDS)
    }

    private companion object {
        const val REFRESH_DELAY_IN_MILLISECONDS = 10_000L
    }
}
