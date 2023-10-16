package com.example.rss_reader

import com.example.rss_reader.html.HtmlWrapper
import com.example.rss_reader.model.XmlRssItem
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.xmlpull.v1.XmlPullParserException

internal class DefaultRssParserTest {

    private val htmlWrapper = mockk<HtmlWrapper> {
        every { parseHtmlText(any()) } answers { firstArg() }
    }

    private val defaultRssParser by lazy {
        DefaultRssParser(htmlWrapper)
    }

    @Test
    fun `Should parse XML item correctly`() {
        val inputStream = RssTestData.testRssInput.byteInputStream()
        val expectedItem = XmlRssItem(
            title = "“thousands” of pieces of content removed",
            description = "This is the description",
            link = "https://this.is.link.com"
        )

        val parsedItems = defaultRssParser.parseInput(inputStream)

        assertThat(expectedItem).isEqualTo(parsedItems[0])
    }

    @Test(expected = XmlPullParserException::class)
    fun `Should throw XmlPullParserException if the rss xml input is not correctly formatted`() {
        val inputStream = RssTestData.invalidTestRssInput.byteInputStream()
        val expectedItem = XmlRssItem(
            title = "“thousands” of pieces of content removed",
            description = "This is the description",
            link = "https://this.is.link.com"
        )

        val parsedItems = defaultRssParser.parseInput(inputStream)

        assertThat(expectedItem).isEqualTo(parsedItems[0])
    }

    @Test
    fun `Should parse XML only item description and title with HTML wrapper`() {
        val title = "“thousands” of pieces of content removed"
        val description = "This is the description"
        val url = "https://this.is.link.com"

        val inputStream = RssTestData.testRssInput.byteInputStream()

        defaultRssParser.parseInput(inputStream)

        verify { htmlWrapper.parseHtmlText(title) }
        verify { htmlWrapper.parseHtmlText(description) }
        verify(exactly = 0) { htmlWrapper.parseHtmlText(url) }
    }
}
