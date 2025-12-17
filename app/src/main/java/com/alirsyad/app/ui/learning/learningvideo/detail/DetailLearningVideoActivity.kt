package com.alirsyad.app.ui.learning.learningvideo.detail

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.alirsyad.app.data.model.detailvideo.DataVideo
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.data.state.DetailCourseState
import com.alirsyad.app.data.state.DetailVideoState
import com.alirsyad.app.data.state.SaveVideoState
import com.alirsyad.app.databinding.ActivityDetailLearningVideoBinding
import com.alirsyad.app.ui.learning.learningvideo.LearningVideoViewModel
import com.alirsyad.app.utils.FullScreenHelper
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.Utils.setupStatusBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailLearningVideoActivity : AppCompatActivity() {

    private val binding : ActivityDetailLearningVideoBinding by lazy {
        ActivityDetailLearningVideoBinding.inflate(layoutInflater)
    }

    private val viewModel: LearningVideoViewModel by viewModels()

    private val fullScreenHelper: FullScreenHelper by lazy{
        FullScreenHelper(this)
    }

    private val progressDialog: Dialog by lazy {
        Utils.progressDialog(this)
    }

    private var idVideo : String = ""
    private var urlVideo : String = ""
    private var youTubePlayerInstance: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDeepLink()
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
        setupObserveViewModel()
        setupVideoPlayer()
        setupStatusBar(this)
    }

    override fun onResume() {
        super.onResume()
        with(binding){
            when(resources.configuration.orientation){
                ORIENTATION_LANDSCAPE -> toolbar.visibility = GONE
                ORIENTATION_PORTRAIT -> toolbar.visibility = VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when(resources.configuration.orientation){
            ORIENTATION_PORTRAIT ->  super.onBackPressed()
        }
    }

    private fun setupDeepLink(){
        try {
            val url: Uri? = intent?.data
            val regex = "video/[\\d]+".toRegex().find(url.toString())!!.value
            idVideo = regex.replace("video/", "")
        } catch (e : Exception) {
            Log.d("DEEPLINK ERROR", e.stackTrace.toString())
        }
    }

    private fun setupViewModel(){
        if (data?.id != null){
            viewModel.getDetailVideon(data?.id!!)
        } else {
            viewModel.getDetailVideon(idVideo.toInt())
        }

        id?.let {
            if (data?.jenjang.isNullOrEmpty() || data?.tingkat.isNullOrEmpty() || data?.name.isNullOrEmpty()) {
                viewModel.getCourseDetail(it)
            }
        }
    }

    private fun setupObserveViewModel(){
        viewModel.detailVideo.observe(this) {
            when (it) {
                is DetailVideoState.Loading -> {
                    setupLoading(true)
                }
                is DetailVideoState.Result -> {
                    setupLoading(false)
                    binding.toolbar.setToolbar(it.data.data.name) {
                        finish()
                    }
                    urlVideo = it.data.data.youtubeId

                    // Setup next and prev buttons
                    setupPrev(it.data.data)
                    setupNext(it.data.data)

                    // Load video when ready
                    binding.ytPlayer.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(urlVideo, 0F)
                        }
                    })
                }
                is DetailVideoState.Error -> {
                    setupError(it)
                }
            }
        }

        viewModel.saveVideo.observe(this) {
            when (it) {
                is SaveVideoState.Loading -> {
                    setupLoading(true)
                }
                is SaveVideoState.Result -> {
                    setupLoading(false)
                    Toast.makeText(this, "Berhasil menonton video!", Toast.LENGTH_SHORT).show()
                }
                is SaveVideoState.Error -> {
                    setupErrorSaveVideo(it)
                }
            }
        }

        viewModel.courseDetail.observe(this) {
            if (it is DetailCourseState.Result) {
                data?.jenjang = it.data.data.tingkat?.jenjang?.name
                data?.tingkat = it.data.data.tingkat?.name
            }
        }
    }

    private fun setupPrev(data : DataVideo){
        with(binding){
            if (data.previous != null){
                btnPrev.visibility = VISIBLE
                btnPrev.setOnClickListener {
                    data.previous.id.let {
                        viewModel.getDetailVideon(it)
                    }
                }
            } else {
                btnPrev.visibility = GONE
            }
        }
    }

    private fun setupNext(data : DataVideo){
        with(binding){
            if (data.next != null){
                btnNext.visibility = VISIBLE
                btnNext.setOnClickListener {
                    data.next.id.let {
                        if (it != null) {
                            viewModel.getDetailVideon(it)
                        }
                    }
                }
            } else {
                btnNext.visibility = GONE
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

    private fun setupError(error: DetailVideoState.Error) {
        setupLoading(false)
    }

    private fun setupErrorSaveVideo(error: SaveVideoState.Error) {
        setupLoading(false)
    }

    private fun setupVideoPlayer() {
        with(binding) {

            ytPlayer.addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    // the video will continue playing in fullscreenView
                    fullScreenHelper.enterFullScreen()
                    ytPlayer.isVisible = false
                }

                override fun onExitFullscreen() {
                    // just exist this activity, we only support playback in fullscreen
                    onBackPressedDispatcher.onBackPressed()
                }
            })

            ytPlayer.also {
                lifecycle.addObserver(it)
                it.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            urlVideo.let { it1 -> youTubePlayer.cueVideo(it1, 0F) }
                            youTubePlayer.play()
                        }

                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) {
                            super.onVideoDuration(youTubePlayer, duration)
                            Log.d("duration", duration.toString())
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            super.onStateChange(youTubePlayer, state)
                            if (PlayerConstants.PlayerState.ENDED == state) {
                                if (data?.id != null){
                                    viewModel.saveFlag(data?.id!!)
                                } else {
                                    viewModel.saveFlag(idVideo.toInt())
                                }
                            }
                        }
                    }, true
                )
                it.enableBackgroundPlayback(true)
            }
        }
    }

    private fun setupToolbar(){
        with(binding){
            toolbar.setToolbar(data?.name) {
                finish()
            }
        }
    }

    val data : DataParams? by lazy {
        intent.getParcelableExtra("data")
    }

    val id : Int? by lazy {
        intent.getIntExtra("id", 0)
    }
}