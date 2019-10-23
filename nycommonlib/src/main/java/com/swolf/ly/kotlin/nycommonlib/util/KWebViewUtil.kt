package com.swolf.ly.kotlin.nycommonlib.util

import android.annotation.SuppressLint
import android.webkit.WebView

object KWebViewUtil {
    @SuppressLint("JavascriptInterface")
    fun loadHtml(webView: WebView, url: String, jsObj: Object, name: String = "jsObj") {
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(jsObj, name)
        webView.loadUrl(url)
    }
}