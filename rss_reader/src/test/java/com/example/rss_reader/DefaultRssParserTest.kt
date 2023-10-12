package com.example.rss_reader

import com.example.rss_reader.model.ParsedRssItem
import org.junit.Test

internal class DefaultRssParserTest {
    private val defaultRssParser by lazy {
        DefaultRssParser()
    }

    @Test
    fun `Should parse XML item correctly`() {
        val inputStream = RssTestData.testRssInput.byteInputStream()
        val expectedItem = ParsedRssItem(
            title = "“thousands” of pieces of content removed",
            description = "This is the description",
            link = "https://this.is.link.com"
        )

        val parsedItems = defaultRssParser.parseInput(inputStream)

        assert(expectedItem == parsedItems[0])
    }
}
