package com.alirsyad.app.ui.verifysuccess

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.databinding.ActivitySuccessVerifyAccountBinding
import com.alirsyad.app.ui.login.LoginActivity
import com.alirsyad.app.utils.Constant.SUCCESS_ACCOUNT_VERIFIED
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessVerifyAccountActivity : AppCompatActivity() {
    private var uri: Uri? = null

    private val binding: ActivitySuccessVerifyAccountBinding by lazy {
        ActivitySuccessVerifyAccountBinding.inflate(layoutInflater)
    }

    private var isSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupSuccess()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupSuccess() = with(binding) {
        uri = intent.data
        if (uri != null) {
            var isRedirected = false
            webview.settings.javaScriptEnabled = true
            webview.settings.domStorageEnabled = true
            webview.webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                        isRedirected = true
                    }
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    if (!isRedirected) {
                        loading.show()
                    }

                    isRedirected = false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isRedirected) {
                        loading.hide()
                        dibSuccess.setType(SUCCESS_ACCOUNT_VERIFIED)
                        dibSuccess.show()
                        isSuccess = true
                        handlerBack()
                    }
                }
            }

            webview.loadUrl(uri.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        handlerBack()
    }

    private fun handlerBack() {
        if (isSuccess) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SuccessVerifyAccountActivity, LoginActivity::class.java))
                finish()
            }, 3000)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}