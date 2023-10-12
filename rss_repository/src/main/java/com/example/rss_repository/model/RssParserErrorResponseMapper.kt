package com.example.rss_repository.model

import com.example.rss_reader.model.RssParserError

internal fun RssParserError.toResponse() = when (this) {
    RssParserError.ConnectionError -> RssParserErrorResponse.ConnectionError
    RssParserError.ParsingError -> RssParserErrorResponse.ParsingError
}
