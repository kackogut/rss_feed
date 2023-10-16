package com.example.rssfeed.feature.rss_reader.list.model

import androidx.annotation.StringRes
import com.example.rssfeed.R

internal sealed class RssParserErrorDisplay(@StringRes val errorDescriptionResource: Int) {
    data object ConnectionError : RssParserErrorDisplay(R.string.error_label_connection_error)
    data object ParsingError : RssParserErrorDisplay(R.string.error_label_parsing_error)
    data object InvalidUrl : RssParserErrorDisplay(R.string.error_label_invalid_url)
    data object UnknownError : RssParserErrorDisplay(R.string.error_label_unknown_error)
}
