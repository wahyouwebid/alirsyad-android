package com.alirsyad.app.component.editext

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentEditTextDisableBinding

class DibEditTextDisable(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){
    private var titleHeader: String? = null
    private var title: String? = null

    private val binding: ComponentEditTextDisableBinding = ComponentEditTextDisableBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibEditTextDisable,
                0,
                0
            ).apply {
                try {
                    titleHeader = getString(R.styleable.DibEditTextDisable_titleHeaderText)
                    title = getString(R.styleable.DibEditTextDisable_titleText)
                } finally {
                    setDefault(titleHeader, title)
                    recycle()
                }
            }
        }
    }

    fun setHeader(string: String) = with(binding) {
        tvHeader.text = string
    }

    fun setText(string: String) = with(binding) {
        tvTitle.text = string
    }

    private fun setDefault(
        header: String? = null,
        title: String? = null,
    ) = with(binding) {
        tvHeader.text = header
        tvTitle.text = title
    }

    fun getString() = binding.tvTitle.text.toString()

}