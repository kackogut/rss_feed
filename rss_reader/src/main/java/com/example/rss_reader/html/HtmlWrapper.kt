package com.example.rss_reader.html

import android.text.Html
import javax.inject.Inject

/**
 * Wrapper that acts as a intermediate between Android [Html] class to allow testing
 * classes that use HTML parsing
 */
class HtmlWrapper @Inject constructor() {

    /**
     * Parses given HTML text
     * @param text - HTML text to parse
     * @return parsed HTML text with all removed tags and unicode characters
     */
    fun parseHtmlText(text: String): String {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}
