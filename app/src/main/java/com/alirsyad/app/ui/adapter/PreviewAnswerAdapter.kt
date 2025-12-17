package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.component.radiobutton.DibRadioPreview
import com.alirsyad.app.data.model.previewadaptive.PreviewAdaptiveSoal
import com.alirsyad.app.databinding.AdapterPreviewAdaptiveBinding
import com.alirsyad.app.ui.learning.learningadaptive.video.PlayVideoPreviewActivity
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.Utils.checkIsSpecialChar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.readHtml
import com.alirsyad.app.utils.show
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class PreviewAnswerAdapter(
    val activity: AppCompatActivity
): RecyclerView.Adapter<PreviewAnswerAdapter.ViewHolder>() {

    private var data = ArrayList<PreviewAdaptiveSoal>()
    private val customCss =
        """
                    <head>
                    <title>Sample HTML</title>
                    <style type='text/css'>
                        @font-face {
                            font-family: 'MyFont';
                            src: url('file:///android_res/font/poppins_regular.ttf') format('truetype');
                        }
                        body, * {
                            font-family: 'MyFont';
                            font-size: 14px;
                            text-align: left;
                            background-color: #F5F5F5;
                        }
                        img {
                            max-width: 100%;
                            height: auto;
                            max-height: 250px;
                            border-radius: 0px;
                        }
                        ol {
                            list-style-type: decimal;
                            margin-left: 0px;
                        }
                        ul {
                            list-style-type: disc;
                            margin-left: 0px;
                        }
                        div {
                            direction: rtl;
                        }
                    </style>
                    </head>
                    """.trimIndent()

    fun setData(itemList: List<PreviewAdaptiveSoal>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.view
        val item = data[position]
        val jawabanList = item.jawaban.orEmpty()
        val options = listOf(binding.dibAnswerA, binding.dibAnswerB, binding.dibAnswerC, binding.dibAnswerD, binding.dibAnswerE)

        // Soal Title
        if (checkIsSpecialChar(item.soal.orEmpty())) {
            binding.tvTitleMath.show()
            binding.tvTitle.hide(true)
            binding.tvTitleMath.text = "${position + 1}. ${item.soal?.readHtml()}"
        } else {
            binding.tvTitleMath.hide(true)
            binding.tvTitle.show()
            binding.tvTitle.text = "${position + 1}. ${item.soal?.readHtml()}"
        }

        // Thumbnail
        if (item.image.isNullOrEmpty()) {
            binding.ivThumbnail.hide(true)
        } else {
            binding.ivThumbnail.show()
            Glide.with(binding.ivThumbnail)
                .load(item.image)
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(binding.ivThumbnail)
        }

        // Set jawaban
        options.forEachIndexed { index, option ->
            option.setTextAnswer(jawabanList.getOrNull(index))
        }

        // Check jawaban siswa
        val selectedIndex = item.jawaban?.indexOf(item.jawabanSiswa) ?: -1
        options.getOrNull(selectedIndex)?.checkAnswer(item, options)

        // Pembahasan
        if (item.pembahasanText == null && item.pembahasanVideo == null) {
            binding.clSeeStudy.hide(true)
        } else if (item.pembahasanVideo == null) {
            binding.ytPlayer.hide(true)
            binding.tvContent.show()
            binding.tvContent.settings.javaScriptEnabled = true
            binding.tvContent.webViewClient = WebViewClient()
            binding.tvContent.loadDataWithBaseURL(
                null,
                customCss + (item.pembahasanText ?: ""),
                "text/html",
                "utf-8",
                null
            )
        } else {
            setupVideoPlayer(item.pembahasanVideo, binding)
            binding.ytPlayer.show()
            binding.tvContent.hide(true)
            binding.llPlayVideo.setOnClickListener {
                activity.startActivity(Intent(activity, PlayVideoPreviewActivity::class.java).apply {
                    putExtra(Constant.Intent.URL_YOUTUBE, item.pembahasanVideo)
                })
            }
        }

        binding.clSeeStudy.setOnClickListener {
            binding.elStudy.toggle()
            val arrowRes = if (binding.elStudy.isExpanded) R.drawable.ic_arrow_top else R.drawable.ic_arrow_bottoms
            binding.tvSeeTheStudy.setCompoundDrawablesWithIntrinsicBounds(0, 0, arrowRes, 0)
        }
    }


    private fun setupVideoPlayer(videoUrl: String?, binding: AdapterPreviewAdaptiveBinding) {
        with(binding) {
            ytPlayer.also {
                activity.lifecycle.addObserver(it)
                it.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            Log.d("YouTubePlayer", "Player ready in adapter, loading video: $videoUrl")

                            // Validate video ID before loading
                            if (!videoUrl.isNullOrEmpty() && videoUrl.isNotBlank()) {
                                try {
                                    youTubePlayer.loadVideo(videoUrl, 0F)
                                } catch (e: Exception) {
                                    Log.e("YouTubePlayer", "Error loading video in adapter: ${e.message}")
                                }
                            } else {
                                Log.e("YouTubePlayer", "Invalid video URL in adapter: $videoUrl")
                            }
                        }

                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) {
                            super.onVideoDuration(youTubePlayer, duration)
                            Log.d("YouTubePlayer", "Video duration: $duration")
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            super.onStateChange(youTubePlayer, state)
                            Log.d("YouTubePlayer", "State changed to: ${state.name}")
                            if (PlayerConstants.PlayerState.ENDED == state) {
                                //
                            }
                        }

                        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                            super.onError(youTubePlayer, error)
                            Log.e("YouTubePlayer", "Player error in adapter: ${error.name}")
                        }
                    }
                )
                it.enableAutomaticInitialization = false
            }
        }
    }

    private fun DibRadioPreview.checkAnswer(
        item: PreviewAdaptiveSoal,
        options: List<DibRadioPreview>
    ) {
        // Reset semua radio button ke default terlebih dahulu
        options.forEach { it.setDefault() }

        if (item.isCorrect != true) {
            // Jika jawaban salah, tampilkan jawaban yang benar dan yang salah
            val correctIndex = item.jawaban?.indexOf(item.jawabanBenar) ?: -1
            options.getOrNull(correctIndex)?.setCorrect()
            this.setWrong()
        } else {
            // Jika jawaban benar, hanya tampilkan yang correct saja
            this.setCorrect()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterPreviewAdaptiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterPreviewAdaptiveBinding) : RecyclerView.ViewHolder(view.root)

}