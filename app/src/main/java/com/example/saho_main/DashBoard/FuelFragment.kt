package com.example.saho_main.DashBoard

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.saho_main.R
import com.example.saho_main.databinding.FragmentFuelBinding


class FuelFragment : Fragment() {
    private lateinit var binding : FragmentFuelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFuelBinding.inflate(inflater,container,false)
        setupWeb()
        return binding.root
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
        webView.loadUrl("https://www.livemint.com/fuel-prices-petrol")

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