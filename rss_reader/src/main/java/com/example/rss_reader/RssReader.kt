package com.example.rss_reader

import com.example.rss_reader.model.ParsedRssItem
import com.example.rss_reader.model.RssParserError
import com.example.rss_reader.model.XmlRssItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

/**
 * Class which allows to fetch and parse RSS feed
 */
interface RssReader {
    /**
     * Fetch and parse Rss from given website
     * @param url - website url from which rss feed will be fetched and parsed
     * @throws RssParserError - in case of connection or parsing issue
     * @return list of parsed items without empty fields
     */
    suspend fun fetchRss(url: String): List<ParsedRssItem>
}

class DefaultRssReader @Inject constructor(
    private val rssParser: RssParser
) : RssReader {

    override suspend fun fetchRss(url: String) = withContext(Dispatchers.IO) {
        (URL(url).openConnection() as? HttpURLConnection)?.run {
            try {
                return@withContext rssParser
                    .parseInput(inputStream)
                    .mapAndFilterEmptyFields()
            } catch (exception: XmlPullParserException) {
                throw RssParserError.ParsingError
            } catch (exception: IOException) {
                throw RssParserError.ConnectionError
            }

        }

        throw RssParserError.ConnectionError
    }

    private fun List<XmlRssItem>.mapAndFilterEmptyFields() = map { xmlListItem ->
        with(xmlListItem) {
            ParsedRssItem(
                description = description ?: "",
                title = title ?: "",
                link = link ?: ""
            ).takeIf {
                description != null && title != null && link != null
            }
        }
    }.filterNotNull()
}
