package com.example.rssfeed.feature.rss_reader.webview

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.rssfeed.design.components.BaseToolbar
import com.example.rssfeed.ui.theme.RssFeedTheme

@Composable
internal fun RssDetailsWebViewRoute(
    url: String,
    onNavigateBack: () -> Unit
) {
    RssFeedTheme {
        RssDetailsWebViewScreen(
            url = url,
            onBackClick = onNavigateBack
        )
    }
}

@Composable
private fun RssDetailsWebViewScreen(
    url: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseToolbar(
                title = url,
                onBackClick = onBackClick
            )
        }
    ) { innerPaddings ->
        val context = LocalContext.current

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .clip(RectangleShape),
            factory = {
                WebView(context).apply {
                    webViewClient = RssDetailsWebViewClient()

                    settings.apply {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        domStorageEnabled = true

                        allowFileAccess = false
                        allowContentAccess = false
                    }

                    loadUrl(url)
                }.apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    }
}
