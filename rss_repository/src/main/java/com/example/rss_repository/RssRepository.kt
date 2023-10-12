package com.example.rss_repository

import com.example.rss_reader.RssReader
import com.example.rss_reader.model.RssParserError
import com.example.rss_repository.model.RssItemResponse
import com.example.rss_repository.model.RssParserErrorResponse
import com.example.rss_repository.model.toResponse
import javax.inject.Inject

interface RssRepository {
    @Throws(RssParserErrorResponse::class)
    suspend fun parseRssUrl(url: String): List<RssItemResponse>
}

class DefaultRssRepository @Inject constructor(
    private val rssReader: RssReader
) : RssRepository {

    override suspend fun parseRssUrl(url: String): List<RssItemResponse> {
        return try {
            rssReader.fetchRss(url).map { it.toResponse() }
        } catch (error: RssParserError) {
            throw error.toResponse()
        }
    }
}
