package com.alirsyad.app.ui.pdfview

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ActivityPdfViewBinding
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class PdfViewActivity : AppCompatActivity() {

    private val binding: ActivityPdfViewBinding by lazy {
        ActivityPdfViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupLoadPdf()
        setupStatusBar(this)
    }

    private fun setupToolbar(){
        with(binding){
            toolbar.setToolbar(getString(R.string.title_banner_detail)){
                finish()
            }
        }
    }

    private fun setupLoadPdf() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val input = URL(url).openStream()
                binding.pdfView.fromStream(input)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .onDrawAll { canvas, pageWidth, pageHeight, _ ->
                        val bitmap =
                            Bitmap.createBitmap(
                                canvas.width,
                                canvas.height,
                                Bitmap.Config.ARGB_8888
                            )
                        val paint = Paint()
                        paint.color = Color.BLACK
                        canvas.drawBitmap(bitmap, pageWidth, pageHeight, paint)
                    }
                    .onLoad {

                    }
                    .onRender { }
                    .linkHandler(DefaultLinkHandler(binding.pdfView))
                    .load()

            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    val url: String? by lazy {
        intent.getStringExtra("url")
    }
}