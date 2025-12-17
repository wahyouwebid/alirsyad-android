package com.alirsyad.app.ui.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.databinding.ActivityPlaySimulationBinding
import com.alirsyad.app.utils.Utils

class PlaySimulationActivity : AppCompatActivity() {

    private val binding : ActivityPlaySimulationBinding by lazy {
        ActivityPlaySimulationBinding.inflate(layoutInflater)
    }

    private val progressDialog: Dialog by lazy {
        Utils.progressDialog(this)
    }

    private lateinit var urlSimulasiDeepLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        setupFullScreen()
        setupDeepLink()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupWebView()
    }

    private fun setupDeepLink(){
        val url: Uri? = intent?.data
        urlSimulasiDeepLink = url.toString().filter { it.isDigit() }
    }

    private fun setupFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(){
        with(binding){
            progressDialog.show()
            wvPlay.settings.javaScriptEnabled = true
            wvPlay.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressDialog.hide()
                }
            }
            if(simulasiUrl.isNullOrEmpty()){
                wvPlay.loadUrl(simulasiUrl)
            } else {
                wvPlay.loadUrl(urlSimulasiDeepLink)
            }

        }
    }

    val id : Int by lazy {
        intent.getIntExtra("id", 0)
    }

    private val simulasiUrl : String by lazy {
        intent.getStringExtra("simulasi_url").toString()
    }
}