package com.alirsyad.app.utils

import android.app.Activity
import android.view.View


class FullScreenHelper(private val context: Activity, vararg views: View) {
    private val views: Array<View> = views as Array<View>

    /**
     * call this method to enter full screen
     */
    fun enterFullScreen() {
        val decorView: View = context.window.decorView
        hideSystemUI(decorView)
        for (view: View in views) {
            view.visibility = View.GONE
            view.invalidate()
        }
    }

    /**
     * call this method to exit full screen
     */
    fun exitFullScreen() {
        val decorView: View = context.window.decorView
        showSystemUI(decorView)
        for (view: View in views) {
            view.visibility = View.VISIBLE
            view.invalidate()
        }
    }

    private fun hideSystemUI(mDecorView: View) {
        mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun showSystemUI(mDecorView: View) {
        mDecorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

}