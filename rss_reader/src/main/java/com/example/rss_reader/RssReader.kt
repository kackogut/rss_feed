package com.example.rss_reader

import com.example.rss_reader.model.ParsedRssItem
import com.example.rss_reader.model.RssParserError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

interface RssReader {
    suspend fun fetchRss(url: String): List<ParsedRssItem>
}

class DefaultRssReader @Inject constructor(
    private val rssParser: RssParser
) : RssReader {

    override suspend fun fetchRss(url: String) = withContext(Dispatchers.IO) {
        (URL(url).openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            doOutput = true

            try {
                return@withContext rssParser.parseInput(inputStream)
            } catch (exception: XmlPullParserException) {
                throw RssParserError.ParsingError
            } catch (exception: IOException) {
                throw RssParserError.ParsingError
            }

        }

        throw RssParserError.ConnectionError
    }
}
