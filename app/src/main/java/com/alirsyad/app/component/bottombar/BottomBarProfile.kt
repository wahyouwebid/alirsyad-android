package com.alirsyad.app.component.bottombar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.LayoutBottomBarBinding

class BottomBarProfile(
    context: Context,
    attributeSet: AttributeSet? = null,
) : LinearLayout(context, attributeSet){

    private val binding: LayoutBottomBarBinding = LayoutBottomBarBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    fun setSelectedMenu(isProfile: Boolean) = with(binding) {
        if (isProfile) {
            ivProfile.background = ContextCompat.getDrawable(context, R.drawable.ic_profile_selected)
            tvProfile.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        } else {
            ivProfile.background = ContextCompat.getDrawable(context, R.drawable.ic_profile)
            tvProfile.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryLighter))
        }
    }


}