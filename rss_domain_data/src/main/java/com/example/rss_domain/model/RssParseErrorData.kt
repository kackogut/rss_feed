package com.example.rss_domain.model

sealed class RssParseErrorData : Exception() {
    data object ConnectionError : RssParseErrorData()
    data object ParsingError : RssParseErrorData()
    data object InvalidUrl : RssParseErrorData()
}
