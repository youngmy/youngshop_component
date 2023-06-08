package com.young.ext

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")
fun WebView.initWebView(body: String, bodyType: Int = 0): WebView {
    //        LogHelper.i("data===", "===initWebView======$body")
    when (bodyType) {
        0 -> {
            //加载网页内容   body 网页内容
            loadData(body, "text/html; charset=UTF-8", null)
        }
        1 -> {
            //加载web资源
            loadUrl(body)
        }
        else -> {
            //加载本地资源 "file:///android_asset/demo.html"
            loadUrl(body)
        }
    }
    val settings = settings
    // 让WebView能够执行javaScript
    settings?.javaScriptEnabled = true
    settings?.defaultTextEncodingName = "UTF-8"
    //设置自适应屏幕，两者合用
    settings?.useWideViewPort = true //将图片调整到适合webview的大小
    settings?.loadWithOverviewMode = true // 缩放至屏幕的大小
    settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

    //        settings.setUseWideViewPort(true);
    //        settings.setLoadWithOverviewMode(true);
    //让JavaScript可以自动打开windows
    settings?.javaScriptCanOpenWindowsAutomatically = true
    //优先使用缓存
    //        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    //不使用缓存
    settings?.cacheMode = WebSettings.LOAD_NO_CACHE

    //从Android5.0开始，WebView默认不支持同时加载Https和Http混合模式
    //在webview加载页面之前，设置加载模式为MIXED_CONTENT_ALWAYS_ALLOW，最好做一下版本判断
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        settings.mixedContentMode = WebSettings.LOAD_NORMAL
    }
    settings.blockNetworkImage = false

    webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                view.loadUrl(url)
            }
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            return true
        }
    }
    webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
//                if (newProgress == 100) {
//                    // 网页加载完成
//
//                } else {
//                    // 加载中
//
//                }
            super.onProgressChanged(view, newProgress)

        }
    }
    return this
}