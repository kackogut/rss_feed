package com.example.rss_repository

import com.example.rss_reader.RssReader
import com.example.rss_repository.model.RssItemResponse
import com.example.rss_repository.model.toResponse
import javax.inject.Inject

interface RssRepository {
    suspend fun parseRssUrl(url: String): List<RssItemResponse>
}

class DefaultRssRepository @Inject constructor(
    private val rssReader: RssReader
) : RssRepository {

    override suspend fun parseRssUrl(url: String): List<RssItemResponse> {
        return rssReader.fetchRss(url).map { it.toResponse() }
    }
}
