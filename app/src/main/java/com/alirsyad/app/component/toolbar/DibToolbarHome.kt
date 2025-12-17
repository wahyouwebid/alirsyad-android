package com.alirsyad.app.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.load
import com.alirsyad.app.R
import com.alirsyad.app.data.model.toolbar.DataToolbar
import com.alirsyad.app.databinding.ComponentToolbarHomeBinding
import com.alirsyad.app.utils.Utils.isNumeric
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.ordinal

class DibToolbarHome(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentToolbarHomeBinding = ComponentToolbarHomeBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setToolbar(
        userName: String?,
        photo: String?,
        data: DataToolbar?
    ) = with(binding) {
        tvUserName.text = userName
        ivAvatar.load(photo) {
            placeholder(R.drawable.ic_profile_default)
            error(R.drawable.ic_profile_default)
        }
        when (data?.pengunjung) {
            0 -> {
                val isNumber = isNumeric(data.tingkat)
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