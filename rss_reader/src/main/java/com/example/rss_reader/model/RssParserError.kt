package com.example.rss_reader.model

sealed class RssParserError : Exception() {
    data object ConnectionError : RssParserError()
    data object ParsingError : RssParserError()
}
