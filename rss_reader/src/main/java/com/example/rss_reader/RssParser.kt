package com.example.rss_reader

import com.example.rss_reader.RssParser.Companion.SUPPORTED_ENCODING
import com.example.rss_reader.html.HtmlWrapper
import com.example.rss_reader.model.XmlRssItem
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
     * @param inputStream - RSS input stream encoded with [SUPPORTED_ENCODING]
     * @throws XmlPullParserException
     * @throws IOException
     */
    fun parseInput(inputStream: InputStream): List<XmlRssItem>

    companion object {
        const val SUPPORTED_ENCODING = "UTF-8"
    }
}

class DefaultRssParser @Inject constructor(
    private val htmlWrapper: HtmlWrapper
) : RssParser {

    override fun parseInput(inputStream: InputStream): List<XmlRssItem> {
        inputStream.use { stream ->
            val parserFactory = XmlPullParserFactory.newInstance()

            val parser = parserFactory.newPullParser().apply {
                setInput(stream, SUPPORTED_ENCODING)
            }

            return readFeed(parser)
        }
    }

    private fun readFeed(parser: XmlPullParser): List<XmlRssItem> {
        var eventType = parser.eventType
        var currentText = ""
        var currentItem = getNewEmptyParsedItem()
        val returnList = mutableListOf<XmlRssItem>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == TAG_NAME_ITEM) currentItem = getNewEmptyParsedItem()
                }

                XmlPullParser.TEXT -> {
                    currentText = parser.text
                }

                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        TAG_NAME_ITEM -> returnList += currentItem
                        TAG_NAME_TITLE -> currentItem.title = parseHtmlText(currentText)
                        TAG_NAME_LINK -> currentItem.link = currentText
                        TAG_NAME_DESCRIPTION -> currentItem.description = parseHtmlText(currentText)
                    }
                }
            }
            eventType = parser.next()
        }

        return returnList
    }

    private fun parseHtmlText(text: String): String = htmlWrapper.parseHtmlText(text)

    private fun getNewEmptyParsedItem() = XmlRssItem(
        title = null,
        description = null,
        link = null
    )

    private companion object {
        const val TAG_NAME_ITEM = "item"
        const val TAG_NAME_TITLE = "title"
        const val TAG_NAME_LINK = "link"
        const val TAG_NAME_DESCRIPTION = "description"
    }
}
