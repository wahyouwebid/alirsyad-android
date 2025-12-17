package com.alirsyad.app.ui.learning.learningmodule.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import com.alirsyad.app.R
import com.alirsyad.app.data.model.detailmodule.DataModule
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailModuleState
import com.alirsyad.app.data.state.SaveModuleState
import com.alirsyad.app.databinding.ActivityDetailLearningModuleWebviewBinding
import com.alirsyad.app.ui.learning.learningmodule.LearningModuleActivity
import com.alirsyad.app.ui.learning.learningmodule.LearningModuleViewModel
import com.alirsyad.app.ui.learning.learningsimulation.LearningSimulationActivity
import com.alirsyad.app.ui.learning.learningvideo.LearningVideoActivity
import com.alirsyad.app.utils.JavascriptInterface
import com.alirsyad.app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import io.github.hyuwah.draggableviewlib.DraggableView
import io.github.hyuwah.draggableviewlib.setupDraggable
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailLearningModuleWebviewActivity : AppCompatActivity() {

    private val binding: ActivityDetailLearningModuleWebviewBinding by lazy {
        ActivityDetailLearningModuleWebviewBinding.inflate(layoutInflater)
    }

    private val viewModel: LearningModuleViewModel by viewModels()

    private val progressDialog: Dialog by lazy {
        Utils.progressDialog(this)
    }

    private lateinit var someDraggableView: DraggableView<LinearLayout>

    private var webView: WebView? = null
    private var webSettings: WebSettings? = null
    private var mUploadMessage: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null
    private var size: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupListener()
        setupViewModel()
        setupObserveViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mUploadMessage == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        try {
            val filePath = mCameraPhotoPath?.replace("file:", "")
            val file = File(filePath)
            size = file.length()
        } catch (e: Exception) {
            Log.e("Error!", "Error while opening image file" + e.localizedMessage)
        }
        if (mCameraPhotoPath != null) {
            var images: ClipData? = null
            try {
                images = data?.clipData
            } catch (e: Exception) {
                Log.e("Error!", e.localizedMessage)
            }
            var results = arrayOf<Uri>()
            // Check that the response is a good one
            if (resultCode == RESULT_OK) {
                if (size != 0L) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        mCameraPhotoPath?.let {
                            results = arrayOf(Uri.parse(it))
                        }
                    }
                } else if (data?.clipData == null) {
                    results = arrayOf(Uri.parse(data?.dataString))
                } else {
                    for (i in 0 until (images?.itemCount ?: 0)) {
                        images?.getItemAt(i)?.uri?.let { uri ->
                            results[i] = uri
                        }
                    }
                }
            }
            mUploadMessage?.onReceiveValue(results)
            mUploadMessage = null
        }
    }

    override fun onBackPressed() {
        if (binding.pdfView.canGoBack()) {
            binding.pdfView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView?.canGoBack() == true) {
            webView?.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    private fun setupToolbar() {
        with(binding) {
            tvTitleName.text = data?.name
        }
    }

    private fun popUpMenu() {
        with(binding) {
            val popupMenu = PopupMenu(this@DetailLearningModuleWebviewActivity, ivMore)
            popupMenu.menuInflater.inflate(R.menu.toolbar_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_module -> goToLearningModule()
                    R.id.action_video -> goToLearningVideo()
                    R.id.action_simulation -> goToLearningSimulation()
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun goToLearningModule() {
        startActivity(Intent(this, LearningModuleActivity::class.java).also {
            val data = DataParams(
                id,
                name,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra("data", data)
        })
    }

    private fun goToLearningVideo() {
        startActivity(Intent(this, LearningVideoActivity::class.java).also {
            val data = DataParams(
                id,
                name,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra("data", data)

        })
    }

    private fun goToLearningSimulation() {
        startActivity(Intent(this, LearningSimulationActivity::class.java).also {
            val data = DataParams(
                id,
                name,
                data?.tingkat,
                data?.jenjang
            )
            it.putExtra("data", data)
        })
    }

    private fun setupViewModel() {
        data?.id?.let {
            viewModel.getDetailModule(it)
        }

        id?.let {
            if (data?.jenjang.isNullOrEmpty() || data?.tingkat.isNullOrEmpty() || data?.name.isNullOrEmpty()) {
                viewModel.getCourseDetail(it)
            }
        }
    }

    private fun setupObserveViewModel() {
        viewModel.detailModule.observe(this, {
            when (it) {
                is DetailModuleState.Loading -> {
                    setupLoading(true)
                }
                is DetailModuleState.Result -> {
                    setupLoadWebview(it.data.data)
                    setupPrev(it.data.data)
                    setupNext(it.data.data)
                }
                is DetailModuleState.Error -> {
                    setupLoading(false)
                    setupError(it)
                }
            }
        })

        viewModel.saveModule.observe(this, {
            when (it) {
                is SaveModuleState.Loading -> {
                    setupLoading(true)
                }
                is SaveModuleState.Result -> {
                    setupLoading(false)
                }
                is SaveModuleState.Error -> {
                    setupLoading(false)
                    setupErrorSave(it)
                }
            }
        })

        viewModel.courseDetail.observe(this, {
            if (it is DetailCourseState.Result) {
                data?.jenjang = it.data.data.tingkat?.jenjang?.name
                data?.tingkat = it.data.data.tingkat?.name
            }
        })
    }

    private fun setupLoading(loading: Boolean) {
//        if (loading) {
//            progressDialog.show()
//        } else {
//            progressDialog.hide()
//        }
    }

    private fun setupError(error: DetailModuleState.Error) {
        if (error.error.message != null) Toast.makeText(
            this,
            error.error.message!!,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupErrorSave(error: SaveModuleState.Error) {
        if (error.error.message != null) Toast.makeText(
            this,
            error.error.message!!,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }

            ivMore.setOnClickListener {
                popUpMenu()
            }

            someDraggableView = llZoom.setupDraggable()
                .setStickyMode(DraggableView.Mode.STICKY_X)
                .setAnimated(true)
                .build()

            cvHide.setOnClickListener {
                llZoom.visibility = GONE
            }

        }
    }

    private fun launchZoomClient() {
        val pm = packageManager
        val intent = pm.getLaunchIntentForPackage("us.zoom.videomeetings")
        intent?.let { startActivity(it) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupLoadWebview(data: DataModule) {
        with(binding) {
            tvTitleName.text = data.name
            verifyStoragePermissions(this@DetailLearningModuleWebviewActivity)
            webView = pdfView
            webView?.invalidate()
            webSettings = webView?.settings
            webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
            webSettings?.databaseEnabled = true
            webSettings?.domStorageEnabled = true
            webSettings?.useWideViewPort = true
            webSettings?.loadWithOverviewMode = true
            webSettings?.javaScriptEnabled = true
            webSettings?.javaScriptCanOpenWindowsAutomatically = true
            webSettings?.allowFileAccess = true
            webSettings?.allowContentAccess = true
            webSettings?.allowUniversalAccessFromFileURLs = true
            webSettings?.allowFileAccessFromFileURLs = true
            webSettings?.pluginState = WebSettings.PluginState.ON
            webView?.addJavascriptInterface(
                JavascriptInterface(this@DetailLearningModuleWebviewActivity),
                "Android"
            )
            webView?.webViewClient = ModulWebViewClient()
            webView?.webChromeClient = ModulWebChromeClient()
            webView?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            webView?.setDownloadListener { url, _, _, _, _ ->
                webView?.loadUrl(
                    JavascriptInterface.getBase64StringFromBlobUrl(url)
                )
            }
            data.pdfViewer?.let { webView?.loadUrl(it) }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }


    private fun setupPrev(data: DataModule) {
        with(binding) {
            if (data.previous != null) {
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

    val data: DataParams? by lazy {
        intent.getParcelableExtra("data")
    }

    val id: Int? by lazy {
        intent.getIntExtra("id", 0)
    }

    val name: String? by lazy {
        intent.getStringExtra("name")
    }

    inner class ModulWebChromeClient : WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            // Double check that we don't have any existing callbacks
            if (mUploadMessage != null) {
                mUploadMessage?.onReceiveValue(null)
            }
            mUploadMessage = filePathCallback
            Log.e("FileCooserParams => ", filePathCallback.toString())
            var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent?.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Unable to create Image File", ex)
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.absolutePath
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                } else {
                    takePictureIntent = null
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            contentSelectionIntent.type = "image/*"
            val intentArray: Array<Intent?> =
                takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(2)
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            startActivityForResult(Intent.createChooser(chooserIntent, "Select images"), 1)
            return true
        }
    }

    inner class ModulWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            // If url contains mailto link then open Mail Intent
            return if (url?.contains("mailto:") == true) {

                // Could be cleverer and use a regex
                //Open links in new browser
                view?.context?.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                )

                // Here we can open new activity
                true
            } else {

                // Stay within this webview and load url
                url?.let { view?.loadUrl(it) }
                true
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {}

        override fun onPageFinished(view: WebView?, url: String?) {
            setupLoading(false)
            webView?.loadUrl(
                "javascript:(function(){ " +
                        "document.getElementById('android-app').style.display='none';})()"
            )
        }
    }

    companion object {
        private const val INPUT_FILE_REQUEST_CODE = 1
        private val TAG = DetailLearningModuleActivity::class.java.simpleName

        // Storage Permissions variables
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        fun verifyStoragePermissions(activity: Activity) {
            // Check if we have read or write permission
            val writePermission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val readPermission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val cameraPermission =
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }
    }
}