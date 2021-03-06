package com.itheamc.naxatestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.itheamc.naxatestapp.databinding.FragmentEntryBinding


class EntryFragment : Fragment() {

    private var _binding: FragmentEntryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Setting AWebViewClient to the web view
         */
        binding.webView.webViewClient = AWebViewClient()


        /**
         * Getting entry link url from the arguments that we have passed from EntriesFragment
         * with key ---> 'link'
         * and loading that url with WebView
         */
        arguments?.let {
            val link = it.get("link")

            link?.let { url ->
                binding.webView.loadUrl(url as String)
            }
        }
    }


    /**
     * Setting binding to null whenever view was destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * Inner class to customize the web view behaviour
     */
    inner class AWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.webProgressBar.visibility = View.GONE
        }
    }
}