package com.alirsyad.app.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.databinding.ComponentToolbarFeaturesRoundedBinding

class DibToolbarFeatureRounded(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentToolbarFeaturesRoundedBinding = ComponentToolbarFeaturesRoundedBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setToolbar(
        title: String?,
    ) = with(binding) {
        tvTitle.text = title
    }

}