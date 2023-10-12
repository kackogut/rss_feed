package com.example.rssfeed.feature.input

data class RssInputState(
    val urlInputText: String,
    val readButtonEnabled: Boolean
) {
    companion object {
        val DEFAULT = RssInputState(
            urlInputText = "",
            readButtonEnabled = false
        )
    }
}
