package com.example.rssfeed.feature.rss_reader.webview

import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Since WebView with enabled JavaScript can bring security issues,
 * this client allows only https communication and disallow file access
 * @param allowedUrl - url to which client would only allow communication
 */
class RssDetailsWebViewClient(
    private val allowedUrl: String
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return when {
            request.url.toString().startsWith("file://") -> true
            request.url.toString() != allowedUrl -> true
            request.url.scheme?.startsWith("https") == true -> false
            else -> true
        }
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        Log.e("RssDetailsWebViewClient", "Received SSL error in DApp browser: $error")
        handler?.cancel()
    }
}
