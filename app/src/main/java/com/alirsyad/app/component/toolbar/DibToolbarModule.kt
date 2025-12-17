package com.alirsyad.app.component.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.databinding.ComponentToolbarModuleBinding

class DibToolbarModule(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentToolbarModuleBinding = ComponentToolbarModuleBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setToolbar(
        title: String?,
        actionBack: () -> Unit,
        actionToggle: (Boolean) -> Unit,
        actionMore: () -> Unit
    ) = with(binding) {
        tvTitle.text = title

        ivBack.setOnClickListener {
            actionBack.invoke()
        }

        switchBab.setOnCheckedChangeListener { _, isChecked ->
            actionToggle(isChecked)
        }

        ivMore.setOnClickListener {
            actionMore.invoke()
        }
    }

    fun getViewMore() = binding.ivMore

}