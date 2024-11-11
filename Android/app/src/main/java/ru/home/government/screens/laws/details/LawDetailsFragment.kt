package ru.home.government.screens.laws.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import ru.home.government.choir.ServerDataTransformer
import ru.home.government.databinding.FragmentLawDetailsBinding
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

    private lateinit var binding: FragmentLawDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLawDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.isClickable = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.allowFileAccessFromFileURLs = true
        binding.webView.settings.allowUniversalAccessFromFileURLs = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = false

        val url = ServerDataTransformer()
            .replaceLawUrlWithExplanatoryNote(requireArguments().getString(EXTRA_DETAILS_URL)!!)
            .plus("#bh_note")
        binding.webView.loadUrl(url)
    }
}