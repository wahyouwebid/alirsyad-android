package com.alirsyad.app.ui.learning.learningsimulation.detail

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.model.detailsimulation.DataDetailSimulation
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.DetailSimulationState
import com.alirsyad.app.databinding.ActivityPlaySimulationBinding
import com.alirsyad.app.ui.learning.learningsimulation.LearningSimulationViewModel
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailLearningSimulationActivity : AppCompatActivity() {

    private val binding: ActivityPlaySimulationBinding by lazy {
        ActivityPlaySimulationBinding.inflate(layoutInflater)
    }

    private val viewModel: LearningSimulationViewModel by viewModels()

    private val progressDialog: Dialog by lazy {
        Utils.progressDialog(this)
    }

    private lateinit var urlSimulasiDeepLink: String
    private lateinit var urlSimulation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        setupFullScreen()
        setupDeepLink()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupObserveViewModel()
    }

    private fun setupDeepLink() {
        try {
            val url: Uri? = intent?.data
            val idSimulasi = "simulasi/[\\d]+".toRegex().find(url.toString())!!.value
            urlSimulasiDeepLink = idSimulasi.replace("simulasi/", "")
        } catch (e: Exception) {
            Log.d("DEEPLINK ERROR", e.stackTrace.toString())
        }
    }

    private fun setupViewModel() {
        if (data?.id != null) {
            data?.id?.let { viewModel.getDetailSimulation(it) }
        } else {
            viewModel.getDetailSimulation(urlSimulasiDeepLink.toInt())
        }
    }

    private fun setupObserveViewModel() {
        viewModel.detailSimulation.observe(this) {
            when (it) {
                is DetailSimulationState.Loading -> {
                    setupLoading(true)
                }
                is DetailSimulationState.Result -> {
                    setupLoading(false)
                    urlSimulation = it.data.data.simulasiUrl
                    setupWebView(it.data.data)
                }
                is DetailSimulationState.Error -> {
                    setupErrorInProgress(it)
                }
            }
        }
    }

    private fun setupLoading(loading: Boolean) {
        if (loading) {
            progressDialog.show()
        } else {
            progressDialog.hide()
        }
    }

    private fun setupErrorInProgress(error: DetailSimulationState.Error) {
        setupLoading(false)
        if (error.error.message != null) Toast.makeText(
            this,
            error.error.message!!,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun setupWebView(detail: DataDetailSimulation) {
        val simulationIdParams = "?simulasi_id="
        val urlParams = "&url="
        val tokenParams = "simulasis/&token="

        val params = if (data?.id != null) {
            "$simulationIdParams${data?.id}$urlParams${BuildConfig.baseUrl}$tokenParams${
                viewModel.getToken().removePrefix(Constant.Bearer)
            }"
        } else {
            "$simulationIdParams${urlSimulasiDeepLink.toInt()}$urlParams${BuildConfig.baseUrl}$tokenParams${
                viewModel.getToken().removePrefix(Constant.Bearer)
            }"
        }

        with(binding) {
            progressDialog.show()
            wvPlay.settings.javaScriptEnabled = true
            wvPlay.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        if (url.startsWith(BuildConfig.baseUrl)) {
                            view?.loadUrl(url + params)
                            Log.i("url-webview", view?.url ?: "")
                        } else {
                            finish()
                        }
                    }
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressDialog.hide()
                }
            }
            wvPlay.loadUrl(detail.simulasiUrl + params)
            Log.i("url-webview", wvPlay.url ?: "")
        }
    }

    val data: DataParams? by lazy {
        intent.getParcelableExtra("data")
    }
}