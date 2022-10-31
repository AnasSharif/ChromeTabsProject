package com.xdeveloperss.chrometabsproject

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.xdeveloperss.chrometabsproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val googleUrl = "https://google.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webSettings: WebSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true

        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        binding.webview.loadUrl(this.googleUrl)
        binding.webview.webViewClient = MyWebViewClient( object : MyWebViewClient.WebViewListener{
            override fun pageLoadFinish() {
                binding.root.postDelayed({
                    val url = "https://stackoverflow.com/"
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
                }, 1000)
            }
        })
    }


}

class MyWebViewClient(var webViewListener: WebViewListener? = null) : WebViewClient(){

    interface WebViewListener{
        fun pageLoadFinish()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        Log.d("MyWebViewClient", error?.description.toString())
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        webViewListener?.pageLoadFinish()
    }

}