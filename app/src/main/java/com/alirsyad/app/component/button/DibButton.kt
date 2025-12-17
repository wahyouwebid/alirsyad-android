package com.alirsyad.app.component.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentButtonPrimaryBinding
import com.alirsyad.app.utils.Constant.ButtonType.OUTLINED
import com.alirsyad.app.utils.Constant.ButtonType.PRIMARY
import com.alirsyad.app.utils.hideKeyboard
import com.alirsyad.app.utils.setOnSingleClickListener

class DibButton(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private var title: String? = null
    private var type: String? = null

    private val binding: ComponentButtonPrimaryBinding = ComponentButtonPrimaryBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibButtonPrimary,
                0,
                0
            ).apply {
                try {
                    title = getString(R.styleable.DibButtonPrimary_buttonText)
                    type = getString(R.styleable.DibButtonPrimary_buttonType)
                } finally {
                    setDefault(title,type)
                    recycle()
                }
            }
        }
    }

    private fun setDefault(
        title: String? = null,
        type: String? = OUTLINED,
    ) = with(binding) {
        button.text = title
        when(type) {
            PRIMARY -> setBackground(R.drawable.bg_button_rounded)
            OUTLINED -> {
                setBackground(R.drawable.bg_button_rounded_stroke)
                button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            else -> setBackground(R.drawable.bg_button_rounded)
        }
    }

    fun setOnClick(onClick:() -> Unit) = with(binding) {
        button.setOnSingleClickListener {
            onClick.invoke()
            hideKeyboard()
        }
    }

    private fun setBackground(background: Int) = with(binding) {
        button.background = ContextCompat.getDrawable(context, background)
    }
}