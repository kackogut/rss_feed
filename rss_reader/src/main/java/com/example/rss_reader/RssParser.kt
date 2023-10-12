package com.example.rss_reader

import com.example.rss_reader.model.ParsedRssItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import javax.inject.Inject

/**
 * Class that allows to parse XML input to response models
 */
interface RssParser {
    /**
     * Parse given stream which should contain valid XML structure
     * @param inputStream - RSS input stream that will be parsed
     * @throws XmlPullParserException
     * @throws IOException
     */
    fun parseInput(inputStream: InputStream): List<ParsedRssItem>
}

class DefaultRssParser @Inject constructor() : RssParser {
    override fun parseInput(inputStream: InputStream): List<ParsedRssItem> {
        inputStream.use { stream ->
            val parserFactory = XmlPullParserFactory.newInstance().apply {
                isNamespaceAware = true
            }

            val parser = parserFactory.newPullParser().apply {
                setInput(stream, null)
            }

            return readFeed(parser)
        }
    }

    private fun readFeed(parser: XmlPullParser): List<ParsedRssItem> {
        var eventType = parser.eventType
        var currentText = ""
        var currentItem = ParsedRssItem()
        val returnList = mutableListOf<ParsedRssItem>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == TAG_NAME_ITEM) currentItem = ParsedRssItem()
                }

                XmlPullParser.TEXT -> {
                    currentText = parser.text
                }

                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        TAG_NAME_ITEM -> returnList += currentItem
                        TAG_NAME_TITLE -> currentItem.title = currentText
                        TAG_NAME_LINK -> currentItem.link = currentText
                        TAG_NAME_DESCRIPTION -> currentItem.description = currentText
                    }
                }
            }
            eventType = parser.next()
        }

        return returnList
    }

    private companion object {
        const val TAG_NAME_ITEM = "item"
        const val TAG_NAME_TITLE = "title"
        const val TAG_NAME_LINK = "link"
        const val TAG_NAME_DESCRIPTION = "description"
    }
}
