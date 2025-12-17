package com.alirsyad.app.component.radiobutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentRadioButtonAnswerNewBinding
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.readHtml
import com.alirsyad.app.utils.show
import com.zanvent.mathview.MathView

class DibRadioAnswerNew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ComponentRadioButtonAnswerNewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var isChecked = false
    private var currentAnswer: String? = null

    fun setIsChecked(isCheckedParam: Boolean) {
        isChecked = isCheckedParam
        binding.rbAnswer.setImageResource(
            if (isCheckedParam) R.drawable.ic_rb_wrong else R.drawable.ic_rb_default
        )
    }

    fun setTitle(answer: String?) {
        currentAnswer = answer
        setTextAnswer(answer)
    }

    private fun setTextAnswer(answer: String?) = with(binding) {
        if (answer.isNullOrBlank()) {
            hideAll()
            return@with
        }

        if (URLUtil.isValidUrl(answer)) {
            showOnly(ivAnswer)
            ivAnswer.load(answer)
        } else {
            checkIsMath(tvAnswer, tvAnswerMath, ivAnswer, answer)
        }
    }

    private fun checkIsMath(
        tvAnswer: TextView,
        tvAnswerMath: MathView,
        ivAnswer: ImageView,
        answer: String
    ) {
        if (Utils.checkIsSpecialChar(answer)) {
            showOnly(tvAnswerMath)
            tvAnswerMath.text = answer
        } else {
            showOnly(tvAnswer)
            tvAnswer.text = answer.readHtml()
        }
        ivAnswer.hide(true)
    }

    fun setChecked(check: Boolean) {
        isChecked = check
        binding.rbAnswer.setImageResource(
            if (check) R.drawable.ic_rb_wrong else R.drawable.ic_rb_default
        )
    }

    fun setEmptyAnswer() {
        isChecked = false
        binding.rbAnswer.setImageResource(R.drawable.ic_rb_empty)
    }

    fun checkEnable(isChecked: Boolean) {
        binding.rbAnswer.setImageResource(
            if (isChecked) R.drawable.ic_rb_wrong else R.drawable.ic_rb_default
        )
    }

    fun setErrorState(showError: Boolean) {
        if (showError) {
            binding.rbAnswer.setImageResource(R.drawable.ic_rb_empty)
        } else {
            binding.rbAnswer.setImageResource(R.drawable.ic_rb_default)
        }
    }

    fun getIsChecked(): Boolean = isChecked

    private fun hideAll() = with(binding) {
        tvAnswer.hide(true)
        tvAnswerMath.hide(true)
        ivAnswer.hide(true)
    }

    private fun showOnly(view: View) = with(binding) {
        listOf(tvAnswer, tvAnswerMath, ivAnswer).forEach {
            if (it == view) it.show() else it.hide(true)
        }
    }
}
