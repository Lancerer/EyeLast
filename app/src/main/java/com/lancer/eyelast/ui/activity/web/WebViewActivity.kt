package com.lancer.eyelast.ui.activity.web

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.databinding.ActivityWebViewBinding
import com.lancer.eyelast.extension.visible
import com.lancer.eyelast.utils.GlobalUtil

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    private var title: String = ""

    private var linkUrl: String = ""

    private var isShare: Boolean = false

    private var isTitleFixed: Boolean = false

    private var mode: Int = MODE_DEFAULT

    companion object {

        const val TAG = "WebViewActivity"

        private const val TITLE = "title"

        private const val LINK_URL = "link_url"

        private const val IS_SHARE = "is_share"

        private const val IS_TITLE_FIXED = "isTitleFixed"

        const val MODE_DEFAULT = 0

        const val MODE_SONIC = 1

        const val MODE_SONIC_WITH_OFFLINE_CACHE = 2

        const val PARAM_MODE = "param_mode"

        const val DEFAULT_URL = "https://github.com/VIPyinzhiwei/Eyepetizer"

        val DEFAULT_TITLE = GlobalUtil.appName

        /**
         * 跳转WebView网页界面
         *
         * @param context       上下文环境
         * @param title         标题
         * @param url           加载地址
         * @param isShare       是否显示分享按钮
         * @param isTitleFixed  是否固定显示标题，不会通过动态加载后的网页标题而改变。true：固定，false 反之。
         * @param mode          加载模式：MODE_DEFAULT 默认使用WebView加载；MODE_SONIC 使用VasSonic框架加载； MODE_SONIC_WITH_OFFLINE_CACHE 使用VasSonic框架离线加载
         */
        fun start(
            context: Context,
            title: String,
            url: String,
            isShare: Boolean = true,
            isTitleFixed: Boolean = true,
            mode: Int = MODE_SONIC
        ) {
            url //预加载url
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(TITLE, title)
                putExtra(LINK_URL, url)
                putExtra(IS_SHARE, isShare)
                putExtra(IS_TITLE_FIXED, isTitleFixed)
                putExtra(PARAM_MODE, mode)
                putExtra(url, System.currentTimeMillis())
            }
            context.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        getBundles()
        initTitleBar()
        initWebView()
        binding.webView.loadUrl(linkUrl)
    }

    private fun getBundles() {
        title = intent.getStringExtra(TITLE) ?: GlobalUtil.appName
        linkUrl = intent.getStringExtra(LINK_URL) ?: DEFAULT_URL
        isShare = intent.getBooleanExtra(IS_SHARE, false)
        isTitleFixed = intent.getBooleanExtra(IS_TITLE_FIXED, false)
        mode = intent.getIntExtra(PARAM_MODE, MODE_DEFAULT)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }

    private fun initTitleBar() {
        binding.titleBar.tvTitle
        if (isShare) binding.titleBar.ivShare.visible()

        binding.titleBar.ivNavigateBefore.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initWebView() {
        binding.webView.settings.run {
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            javaScriptEnabled = true
            binding.webView.removeJavascriptInterface("searchBoxJavaBridge_")
//            intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
//            binding.webView.addJavascriptInterface(SonicJavaScriptInterface(sonicSessionClient, intent), "sonic")
            allowContentAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            setAppCacheEnabled(true)
            savePassword = false
            saveFormData = false
            useWideViewPort = true
            loadWithOverviewMode = true
            defaultTextEncodingName = "UTF-8"
            setSupportZoom(true)
        }
        binding.webView.webChromeClient = UIWebChromeClient()
        binding.webView.webViewClient = UIWebViewClient()
        binding.webView.setDownloadListener { url, _, _, _, _ ->
            // 调用系统浏览器下载
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    inner class UIWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            Log.d(TAG, "onPageStarted >>> url:${url}")
            linkUrl = url
            super.onPageStarted(view, url, favicon)
            binding.progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            Log.d(TAG, "onPageFinished >>> url:${url}")
            super.onPageFinished(view, url)
            //    sonicSession?.sessionClient?.pageFinish(url)
            binding.progressBar.visibility = View.INVISIBLE
        }

        override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
//            if (sonicSession != null) {
//                val requestResponse = sonicSessionClient?.requestResource(url)
//                if (requestResponse is WebResourceResponse) return requestResponse
//            }
            return null
        }
    }

    inner class UIWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            Log.d(TAG, "onReceivedTitle >>> title:${title}")
            if (!isTitleFixed) {
                title?.run {
                    this@WebViewActivity.title = this
                    binding.titleBar.tvTitle.text = this
                }
            }
        }
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.activity_web_view
}