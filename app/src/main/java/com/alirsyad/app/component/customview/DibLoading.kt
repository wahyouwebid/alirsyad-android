package com.alirsyad.app.component.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentLoadingBinding
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show

class DibLoading(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){
    private var loadingType: String? = null

    private val binding: ComponentLoadingBinding = ComponentLoadingBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibLoading,
                0,
                0
            ).apply {
                try {
                    loadingType = getString(R.styleable.DibLoading_loadingType)
                } finally {
                    setDefault(loadingType)
                    recycle()
                }
            }
        }
    }

    private fun setDefault(
        type: String? = Constant.LoadingType.TRANSPARENT,
    ) {
        when(type) {
            Constant.LoadingType.TRANSPARENT -> setBackground(R.color.transparent)
            Constant.LoadingType.WITH_BACKGROUND -> setBackground(R.color.white)
            else -> setBackground(R.color.transparent)
        }
    }

    fun setLoading(
        isLoading: Boolean
    ) = with(binding) {
        when(isLoading) {
            true -> {
                root.show()
                lottie.show()
            }
            else -> {
                root.hide(true)
                lottie.hide(true)
            }
        }
    }

    private fun setBackground(background: Int) = with(binding) {
        root.background = ContextCompat.getDrawable(context, background)
    }
}