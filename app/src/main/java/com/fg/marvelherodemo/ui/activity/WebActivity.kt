package com.fg.marvelherodemo.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fg.marvelherodemo.R
import kotlinx.android.synthetic.main.activity_web.*
import java.lang.Exception
import android.webkit.WebView
import android.webkit.WebViewClient




class WebActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent.getStringExtra("URL")

            webView.loadUrl(url)
            webView.webViewClient = CustomWebViewClient()
            webView.settings.javaScriptEnabled = true

    }

    inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
