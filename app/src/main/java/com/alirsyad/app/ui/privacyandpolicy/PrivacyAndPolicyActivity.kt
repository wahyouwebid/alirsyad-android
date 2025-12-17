package com.alirsyad.app.ui.privacyandpolicy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ActivityPrivacyAndPolicyBinding
import com.alirsyad.app.utils.Utils.setupStatusBar

class PrivacyAndPolicyActivity : AppCompatActivity() {

    private val binding: ActivityPrivacyAndPolicyBinding by lazy {
        ActivityPrivacyAndPolicyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupStatusBar(this)
        setupAppVersion()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.setToolbar(getString(R.string.title_privacy_and_policy)){
            finish()
        }
    }

    private fun setupAppVersion() {
        with(binding) {
            tvAppVersion.text = String.format("Version ${BuildConfig.VERSION_NAME}")
        }
    }
}