package com.alirsyad.app.ui.learning.learningadaptive.video

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alirsyad.app.databinding.ActivityPlayVideoPreviewBinding
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.FullScreenHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayVideoPreviewActivity : AppCompatActivity() {

    private val binding: ActivityPlayVideoPreviewBinding by lazy {
        ActivityPlayVideoPreviewBinding.inflate(layoutInflater)
    }

    private val fullScreenHelper: FullScreenHelper by lazy{
        FullScreenHelper(this)
    }

    private val videoUrl: String? by lazy {
        intent?.getStringExtra(Constant.Intent.URL_YOUTUBE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupVideoPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.ytPlayer.release()
    }

    private fun setupVideoPlayer() {
        with(binding) {
            ytPlayer.also {
                lifecycle.addObserver(it)
                it.addFullscreenListener(object : FullscreenListener {
                    override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        fullScreenHelper.enterFullScreen()
                    }

                    override fun onExitFullscreen() {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        fullScreenHelper.exitFullScreen()
                    }
                })

                it.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            Log.d("YouTubePlayer", "Player ready, loading video: $videoUrl")

                            // Validate video ID before loading with local variable to allow smart cast
                            val url = videoUrl
                            if (!url.isNullOrEmpty() && url.isNotBlank()) {
                                try {
                                    youTubePlayer.loadVideo(url, 0F)
                                } catch (e: Exception) {
                                    Log.e("YouTubePlayer", "Error loading video: ${e.message}")
                                }
                            } else {
                                Log.e("YouTubePlayer", "Invalid video URL: $url")
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
                        }

                        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                            super.onError(youTubePlayer, error)
                            Log.e("YouTubePlayer", "Player error: ${error.name}")
                        }
                    }
                )
                it.enableAutomaticInitialization = false
            }
        }
    }

}