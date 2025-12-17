package com.alirsyad.app.component.radiobutton

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.load
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentRadioButtonAnswerBinding
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.readHtml
import com.alirsyad.app.utils.show
import com.zanvent.mathview.MathView


class DibRadioAnswer(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentRadioButtonAnswerBinding = ComponentRadioButtonAnswerBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setTextAnswer(answer: String?) = with(binding){
        if (URLUtil.isValidUrl(answer)) {
            ivAnswer.show()
            ivAnswer.load(answer)
        } else {
            checkIsMath(tvAnswer, tvAnswerMath, ivAnswer, answer)
        }
    }

    private fun checkIsMath(
        tvAnswer: TextView,
        tvAnswerMath: MathView,
        ivAnswer: ImageView,
        answer: String?,
    ) {
        if (Utils.checkIsSpecialChar(answer!!)) {
            ivAnswer.hide(true)
            tvAnswer.hide(true)
            tvAnswerMath.show()
            tvAnswerMath.text = answer
        } else {
            ivAnswer.hide(true)
            tvAnswerMath.hide(true)
            tvAnswer.show()
            tvAnswer.text = answer.readHtml()
        }
    }

    fun setChecked(isChecked: Boolean) = with(binding) {
        rbAnswer.isChecked = isChecked
    }

    fun getIsChecked(): Boolean{
        return binding.rbAnswer.isChecked
    }

    fun setButtonTintList(colorState: ColorStateList) = with(binding) {
        rbAnswer.buttonTintList = colorState
    }

    private fun getColor(context: Context) = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled)
        ), intArrayOf(
            ContextCompat.getColor(context, R.color.colorAccentDark),  // enabled
            ContextCompat.getColor(context, R.color.neutral_50),  // disabled
        )
    )

}