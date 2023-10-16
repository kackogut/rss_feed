package com.example.rssfeed.feature.rss_reader.list

import com.example.rss_domain.SubscribeToRssFeedFromUrlUseCase
import com.example.rss_domain.model.RssFeedItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class MockSubscribeToRssFeedUseCase : SubscribeToRssFeedFromUrlUseCase {
    private var flow = MutableSharedFlow<Result<List<RssFeedItemData>>>()

    override fun execute(url: String): Flow<Result<List<RssFeedItemData>>> = flow

    suspend fun addValue(value: Result<List<RssFeedItemData>>) = flow.emit(value)

    fun clear() {
        flow = MutableSharedFlow()
    }
}
