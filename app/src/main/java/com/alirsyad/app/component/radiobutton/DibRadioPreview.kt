package com.alirsyad.app.component.radiobutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.load
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentPreviewAnswerBinding
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.readHtml
import com.alirsyad.app.utils.show
import com.zanvent.mathview.MathView


class DibRadioPreview(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentPreviewAnswerBinding = ComponentPreviewAnswerBinding.inflate(
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

    fun setCorrect() = with(binding){
        clAnswer.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_correct)
        rbAnswer.background = ContextCompat.getDrawable(rbAnswer.context, R.drawable.ic_rb_correct)

    }

    fun setDefault() = with(binding) {
        clAnswer.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_default)
        rbAnswer.background = ContextCompat.getDrawable(rbAnswer.context, R.drawable.ic_rb_default)
    }

    fun setWrong() = with(binding) {
        clAnswer.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_wrong)
        rbAnswer.background = ContextCompat.getDrawable(rbAnswer.context, R.drawable.ic_rb_wrong)
    }

}