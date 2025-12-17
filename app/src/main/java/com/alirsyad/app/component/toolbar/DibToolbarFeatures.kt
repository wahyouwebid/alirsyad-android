package com.alirsyad.app.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.databinding.ComponentToolbarFeaturesBinding

class DibToolbarFeatures(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentToolbarFeaturesBinding = ComponentToolbarFeaturesBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setToolbar(
        title: String?,
    ) = with(binding) {
        tvTitle.text = title
    }

}