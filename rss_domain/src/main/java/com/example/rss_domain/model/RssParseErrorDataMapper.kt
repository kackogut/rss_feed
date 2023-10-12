package com.example.rss_domain.model

import com.example.rss_repository.model.RssParserErrorResponse

internal fun RssParserErrorResponse.toDomainModel() = when (this) {
    RssParserErrorResponse.ConnectionError -> RssParseErrorData.ConnectionError
    RssParserErrorResponse.ParsingError -> RssParseErrorData.ParsingError
}
