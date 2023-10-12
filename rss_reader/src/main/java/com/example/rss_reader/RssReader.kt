package com.example.rss_reader

import com.example.rss_reader.model.ParsedRssItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

interface RssReader {
    suspend fun fetchRss(url: String): List<ParsedRssItem>
}

internal class DefaultRssReader(
    private val rssParser: RssParser
) : RssReader {

    override suspend fun fetchRss(url: String) = withContext(Dispatchers.IO) {
        (URL(url).openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            doOutput = true
            return@withContext rssParser.parseInput(inputStream)
        }

        throw Exception()
    }
}
