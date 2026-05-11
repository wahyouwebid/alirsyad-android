package com.alirsyad.app.ui.learning.learningmodule.detail

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.detailmodule.DataModule
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailModuleState
import com.alirsyad.app.data.state.SaveModuleState
import com.alirsyad.app.databinding.ActivityDetailLearningModuleBinding
import com.alirsyad.app.ui.learning.learningmodule.LearningModuleViewModel
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.Constant.Intent.DATA
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.openDownloadedPDF
import com.alirsyad.app.utils.show
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class DetailLearningModuleActivity : AppCompatActivity() {

    private var data : DataParams? = null
    private var id : Int? = null
    private var name : String? = null
    private var dataModule : DataModule? = null
    private val viewModel: LearningModuleViewModel by viewModels()
    private val binding: ActivityDetailLearningModuleBinding by lazy {
        ActivityDetailLearningModuleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupDataIntent()
        setupToolbar()
        setupListener()
        setupViewModel()
        setupObserveViewModel()
        setupStatusBar(this)
    }

    private fun setupDataIntent() {
        data = intent.getParcelableExtra(DATA)
        id = intent.getIntExtra(Constant.Intent.ID, 0)
        name =  intent.getStringExtra(Constant.Intent.NAME)
    }

    private fun setupToolbar(){
        with(binding){
            toolbar.setToolbar(data?.name,
                actionBack = { finish() },
                actionToggle = { setupToggle(it) },
                actionMore = { popUpMenu() }
            )
        }
    }

    private fun setupToggle(isChecked: Boolean) = with(binding) {
        when(isChecked){
            true -> {
                cvDraw.visibility = VISIBLE
                canvas.visibility = VISIBLE
                setupDrawing()
            }
            false -> {
                cvDraw.visibility = GONE
                canvas.visibility = GONE
                cvColor.visibility = GONE
            }
        }
    }

    private fun popUpMenu(){
        with(binding){
            if (cvDownloadPdf.isShown) {
                cvDownloadPdf.hide()
                areaContent.hide(true)
            } else {
                cvDownloadPdf.show()
                areaContent.show()
            }

            areaContent.setOnClickListener {
                cvDownloadPdf.hide(true)
                areaContent.hide(true)
            }

            tvPrintModule.setOnClickListener {
                checkPermission(0)
                cvDownloadPdf.hide(true)
                areaContent.hide(true)
            }

            tvDownloadModule.setOnClickListener {
                checkPermission(1)
                cvDownloadPdf.hide(true)
                areaContent.hide(true)
            }
        }
    }

    private fun checkPermission(position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (position == 0) printModulePdf() else downloadModulePdf()
        } else {
            when {
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    if (position == 0) printModulePdf() else downloadModulePdf()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    Toast.makeText(
                        applicationContext,
                        "Permission is needed to activate this feature.",
                        Toast.LENGTH_SHORT
                    ).show()
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }


    private fun setupViewModel(){
        data?.id?.let {
            viewModel.getDetailModule(it)
        }

        id?.let {
            if (data?.jenjang.isNullOrEmpty() || data?.tingkat.isNullOrEmpty() || data?.name.isNullOrEmpty()) {
                viewModel.getCourseDetail(it)
            }
        }
    }

    private fun setupObserveViewModel(){
        viewModel.detailModule.observe(this) {
            when (it) {
                is DetailModuleState.Loading -> {}
                is DetailModuleState.Result -> {
                    setupLoadPdf(it.data.data)
                    setupPrev(it.data.data)
                    setupNext(it.data.data)
                }
                is DetailModuleState.Error -> {}
            }
        }

        viewModel.saveModule.observe(this) {
            when (it) {
                is SaveModuleState.Loading -> {}
                is SaveModuleState.Result -> {}
                is SaveModuleState.Error -> {}
            }
        }

        viewModel.courseDetail.observe(this) {
            if (it is DetailCourseState.Result) {
                data?.jenjang = it.data.data.tingkat?.jenjang?.name
                data?.tingkat = it.data.data.tingkat?.name
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            cvHide.setOnClickListener {
                llZoom.visibility = GONE
            }
        }
    }

    private fun setupLoadPdf(data : DataModule) {
        dataModule = data
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val input = URL(data.pdfPath).openStream()
                binding.pdfView.fromStream(input)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .onDrawAll { canvas, pageWidth, pageHeight, _ ->
                        val bitmap =
                            Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
                        val paint = Paint()
                        paint.color = Color.BLACK
                        canvas.drawBitmap(bitmap, pageWidth, pageHeight, paint)
                    }
                    .onLoad {

                    }
                    .onRender {  }
                    .linkHandler(DefaultLinkHandler( binding.pdfView))
                    .load()

            }catch (e : Exception) {
                Log.e("error",e.toString())
            }
        }
    }

    private fun setupPrev(data : DataModule){
        with(binding){
            if (data.previous != null){
                btnPrev.visibility = VISIBLE
                btnPrev.setOnClickListener {
                    data.previous.id.let {
                        viewModel.getDetailModule(it)
                    }
                }
            } else {
                btnPrev.visibility = GONE
            }
        }
    }

    private fun setupNext(data: DataModule) {
        with(binding) {
            btnNext.visibility = VISIBLE
            if (data.next != null) {
                btnNext.text = getString(R.string.title_modul_next)
                btnNext.setOnClickListener {
                    data.id?.let { it1 -> viewModel.saveFlag(it1) }
                    data.next.id?.let { it1 -> viewModel.getDetailModule(it1) }
                }
            } else {
                btnNext.text = getString(R.string.title_modul_finish)
                btnNext.setOnClickListener {
                    data.id?.let { it1 -> viewModel.saveFlag(it1) }
                    finish()
                }
            }
        }
    }

    private fun setupDrawing(){
        with(binding){
            canvas.setStrokeColor(Color.BLACK)
            llUndo.setOnClickListener {
                canvas.undoMove()
            }

            llClear.setOnClickListener {
                canvas.clearCanvas()
            }

            llColor.setOnClickListener {
                if (cvColor.isShown){
                    cvColor.visibility = GONE
                } else {
                    cvColor.visibility = VISIBLE
                }
            }

            llRedo.setOnClickListener {
                canvas.redoMove()
            }

            llColorBlack.setOnClickListener {
                canvas.setStrokeColor(Color.BLACK)
                cvColor.visibility = GONE
            }

            llColorRed.setOnClickListener {
                canvas.setStrokeColor(Color.RED)
                cvColor.visibility = GONE
            }

            llColorBlue.setOnClickListener {
                canvas.setStrokeColor(Color.BLUE)
                cvColor.visibility = GONE
            }

            llColorYellow.setOnClickListener {
                canvas.setStrokeColor(Color.YELLOW)
                cvColor.visibility = GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadCastDownloadPrintComplete)
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadCastDownloadComplete)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            broadCastDownloadPrintComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            broadCastDownloadComplete,
            IntentFilter(DownloadManager.ACTION_VIEW_DOWNLOADS)
        )
    }

    private fun printModulePdf() {
        val url = dataModule?.pdfPath
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("${dataModule?.name}.pdf")
            .setDescription("Downloading ${dataModule?.name}.pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${dataModule?.name}.pdf")

        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentBroadCast = Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentBroadCast)
        }, 1000)
    }

    private fun downloadModulePdf() {
        val url = dataModule?.pdfPath
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("${dataModule?.name}.pdf")
            .setDescription("Downloading ${dataModule?.name}.pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${dataModule?.name}.pdf")

        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentBroadCast = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentBroadCast)
        }, 1000)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            printModulePdf()
        }
        else Toast.makeText(
            this,
            "Permission is needed to activate this feature.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private var broadCastDownloadPrintComplete = object : BroadcastReceiver() {
        override fun onReceive(contex: Context?, p1: Intent?) {
            openDownloadedPDF("${dataModule?.name}.pdf")
        }
    }

    private var broadCastDownloadComplete = object : BroadcastReceiver() {
        override fun onReceive(contex: Context?, p1: Intent?) {
            Toast.makeText(applicationContext, "File Berhasil di Unduh", Toast.LENGTH_SHORT).show()
        }
    }


}