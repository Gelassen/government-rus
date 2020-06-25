package ru.home.government.screens.laws.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_law_details.*
import ru.home.government.App
import ru.home.government.R
import ru.home.government.screens.BaseFragment

class LawDetailsFragment: BaseFragment() {

    companion object {

        private const val EXTRA_DETAILS_URL = "EXTRA_DETAILS_URL"

        fun instance(url: String): LawDetailsFragment {
            val fragment = LawDetailsFragment()
            val args = Bundle()
            args.putString(EXTRA_DETAILS_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_law_details, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.isClickable = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true

        val url = arguments!!.getString(EXTRA_DETAILS_URL)!!.plus("#bh_note")
        webView.loadUrl(url)
    }
}