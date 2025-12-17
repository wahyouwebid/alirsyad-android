package com.alirsyad.app.component.dropdown

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentSpinnerBinding
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show

class DibSpinner(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){
    private var titleHeaderSpinner: String? = null
    private var titleSpinner: String? = null
    private var titleErrorSpinner: String? = null

    private val binding: ComponentSpinnerBinding = ComponentSpinnerBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibSpinner,
                0,
                0
            ).apply {
                try {
                    titleHeaderSpinner = getString(R.styleable.DibSpinner_titleHeaderSpinner)
                    titleSpinner = getString(R.styleable.DibSpinner_titleSpinner)
                    titleErrorSpinner = getString(R.styleable.DibSpinner_titleErrorSpinner)
                } finally {
                    setDefault(titleHeaderSpinner, titleSpinner, titleErrorSpinner)
                    recycle()
                }
            }
        }
    }

    fun setTitleHeader(string: String) = with(binding) {
        tvTitle.text = string
    }

    fun setTitleHint(string: String) = with(binding) {
        tvInput.hint = string
    }

    fun hideError() {
        binding.tvError.hide(true)
    }

    fun validateForm() = with(binding) {
        if (tvInput.text ==context.getText(R.string.title_form_hint_level)) {
            tvError.show()
        } else {
            tvError.hide(true)
        }
    }

    fun formNotEmpty() : Boolean = with(binding){
        return tvInput.text != context.getText(R.string.title_form_hint_level)
    }

    private fun setDefault(
        header: String? = null,
        hint: String? = null,
        error: String? = null,
    ) = with(binding) {
        tvTitle.text = header
        tvInput.hint = hint
        tvError.text = if (error.isNullOrEmpty()) context.getText(R.string.title_edit_text_error) else error
    }

    fun getString() = binding.tvInput.text.toString()

    fun setText(string: String) {
        binding.tvInput.text = string
    }

}