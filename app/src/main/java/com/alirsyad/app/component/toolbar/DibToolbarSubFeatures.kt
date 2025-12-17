package com.alirsyad.app.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.databinding.ComponentToolbarSubFeaturesBinding
import com.alirsyad.app.utils.Constant.Level.EASY
import com.alirsyad.app.utils.Constant.Level.HARD
import com.alirsyad.app.utils.Constant.Level.MEDIUM
import com.alirsyad.app.utils.Utils
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.ordinal
import com.alirsyad.app.utils.show

class DibToolbarSubFeatures(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentToolbarSubFeaturesBinding = ComponentToolbarSubFeaturesBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setToolbar(
        title: String?,
        data: DataToolbar? = null,
        level: String? = null,
        actionBack: () -> Unit
    ) = with(binding) {
        tvTitle.text = title
        if (data == null) {
            tvClass.hide(true)
            setLevel(level)
        } else {
            tvClass.show()
            setData(data)
        }

        ivBack.setOnClickListener {
            actionBack.invoke()
        }
    }

    private fun setLevel(level: String?) = with(binding) {
        when (level) {
            EASY -> {
                ivLevel.show()
                ivLevel.background = ContextCompat.getDrawable(context, R.drawable.ic_indicator_level_easy)
            }

            MEDIUM -> {
                ivLevel.show()
                ivLevel.background = ContextCompat.getDrawable(context, R.drawable.ic_indicator_level_medium)
            }

            HARD -> {
                ivLevel.show()
                ivLevel.background = ContextCompat.getDrawable(context, R.drawable.ic_indicator_level_hard)
            }

            else -> ivLevel.hide(true)
        }
    }

    private fun setData(data: DataToolbar?) = with(binding){
        when (data?.pengunjung) {
            0 -> {
                val isNumber = Utils.isNumeric(data.tingkat)
                if (isNumber == true) {
                    data.tingkat?.toInt()?.let { changeColor(it) }
                    tvClass.text = context.getString(
                        R.string.title_class_of_1st, data.tingkat?.toInt()?.ordinal()
                    )
                } else {
                    tvClass.text = String.format("TK ${data.tingkat}")
                    tvClass.background = ContextCompat.getDrawable(context, R.drawable.bg_circle_purple)
                }
            }
            else -> tvClass.hide(true)
        }
    }
    private fun changeColor(classes: Int) = with(binding){
        if (classes > 6) {
            tvClass.background = ContextCompat.getDrawable(context, R.drawable.bg_circle_blue)
        } else {
            tvClass.background = ContextCompat.getDrawable(context, R.drawable.bg_circle_red)
        }
    }

}