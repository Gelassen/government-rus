package ru.home.government.screens.laws.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_law_details.*
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
        webView!!.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        val url = arguments!!.getString(EXTRA_DETAILS_URL)!!.plus("#bh_note")
        webView.loadUrl(url)
    }
}