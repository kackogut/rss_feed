package com.example.rss_reader.model

/**
 * Returned RSS item which contains all required fields
 */
data class ParsedRssItem(
    val title: String,
    val description: String,
    val link: String
)
