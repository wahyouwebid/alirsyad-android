package com.alirsyad.app.component.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.databinding.ComponentEmptyStateBinding

class DibEmptyState(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentEmptyStateBinding = ComponentEmptyStateBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setData(
        title: String?,
        subTitle: String?
    ) {
        with(binding) {
            tvTitle.text = title
            tvSubTitle.text = subTitle
        }
    }
}