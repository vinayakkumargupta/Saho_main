package com.example.saho_main.Emergency

import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.saho_main.R
import com.example.saho_main.databinding.ActivityEmergencyBinding
import com.example.saho_main.databinding.ActivityQuickshareBinding

class EmergencyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyBinding
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWeb()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupWeb() {
        val webView = binding.webView1
        webView.settings.javaScriptEnabled = true // Enable JavaScript
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.loadsImagesAutomatically = true

        // Enable mixed content mode
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // Enable zoom control
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true

        // Apply dark theme
        webView.settings.forceDark = WebSettings.FORCE_DARK_ON

        // Load the URL
        webView.loadUrl("https://www.verywellhealth.com/basic-first-aid-procedures-1298578")

        // Set WebViewClient to handle page navigation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
    }
}