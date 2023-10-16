package com.example.rssfeed.feature.rss_reader.list.model

import com.example.rss_domain.model.RssParseErrorData

internal fun RssParseErrorData.toDisplay() = when (this) {
    RssParseErrorData.ConnectionError -> RssParserErrorDisplay.ConnectionError
    RssParseErrorData.ParsingError -> RssParserErrorDisplay.ParsingError
    RssParseErrorData.InvalidUrl -> RssParserErrorDisplay.InvalidUrl
}
