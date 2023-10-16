package com.example.rss_repository.model

sealed class RssParserErrorResponse : Exception() {
    data object ConnectionError : RssParserErrorResponse()
    data object ParsingError : RssParserErrorResponse()
    data object InvalidUrl : RssParserErrorResponse()
}
