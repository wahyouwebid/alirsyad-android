package com.alirsyad.app.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.alirsyad.app.R
import com.alirsyad.app.data.model.intro.DataIntro
import com.alirsyad.app.databinding.ActivityIntroBinding
import com.alirsyad.app.ui.adapter.IntroAdapter
import com.alirsyad.app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {

    private val binding: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val viewModel : IntroViewModel by viewModels()

    private val adapterIntro: IntroAdapter by lazy {
        IntroAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAdapter()
        setupListener()
    }

    private fun setupListener() {
        with(binding) {
            btnNext.setOnClickListener {
                vpIntro.setCurrentItem(getItem(+1), true)
            }

            btnSkip.setOnClickListener {
                viewModel.setDataIntro(true)
                startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
                finish()
            }

            vpIntro.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 3) {
                        btnNext.text = getString(R.string.title_get_started)
                    } else {
                        btnNext.text = getString(R.string.title_next)
                    }
                }
            })
        }
    }

    private fun getItem(position: Int): Int {
        with(binding) {
            if (vpIntro.currentItem == 3) {
                viewModel.setDataIntro(true)
                startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
                finish()
            }
            return vpIntro.currentItem + position
        }
    }

    private fun setupAdapter() {
        with(binding) {
            adapterIntro.setData(listOf(
                DataIntro(
                    "Curricullum Integrated",
                    "integrate the curriculum from every level in the Al-Irsyad Al-Islamiyah school in order to achieve sustainable education at every level.",
                    ContextCompat.getDrawable(this@IntroActivity, R.drawable.intro_slide_1)
                ),
                DataIntro(
                    "Learning Matters",
                    "Your learning experience will be different from before. You can learn much more broadly and interestingly than you have done so far.",
                    ContextCompat.getDrawable(this@IntroActivity, R.drawable.intro_slide_2)
                ),
                DataIntro(
                    "Digital Interactive Books",
                    "With this concept, you can access not only the text of a lesson, it is also equipped with various kinds of interactive learning videos",
                    ContextCompat.getDrawable(this@IntroActivity, R.drawable.intro_slide_3)
                ),
                DataIntro(
                    "Education Technology",
                    "The use of technology in this teaching and learning process will make you addicted to learning, your knowledge will increase a lot.",
                    ContextCompat.getDrawable(this@IntroActivity, R.drawable.intro_slide_4)
                ),

            ))
            vpIntro.adapter = adapterIntro
            dotsIntro.setViewPager2(vpIntro)
        }
    }
}