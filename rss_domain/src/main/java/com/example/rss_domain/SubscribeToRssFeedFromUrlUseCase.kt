package com.example.rss_domain

import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.toDomainModel
import com.example.rss_repository.RssRepository
import com.example.rss_repository.model.RssParserErrorResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

interface SubscribeToRssFeedFromUrlUseCase {
    fun execute(url: String): Flow<Result<List<RssFeedItemData>>>
}

class DefaultSubscribeToRssFeedFromUrlUseCase @Inject constructor(
    private val rssRepository: RssRepository
) : SubscribeToRssFeedFromUrlUseCase {
    override fun execute(url: String): Flow<Result<List<RssFeedItemData>>> = flow {
        while (currentCoroutineContext().isActive) {
            try {
                emit(Result.success(rssRepository.parseRssUrl(url).map { it.toDomainModel() }))
            } catch (exception: RssParserErrorResponse) {
                emit(Result.failure(exception.toDomainModel()))
            }

            delay(REFRESH_DELAY_IN_MILLISECONDS)
        }
    }

    private companion object {
        const val REFRESH_DELAY_IN_MILLISECONDS = 10_000L
    }
}
