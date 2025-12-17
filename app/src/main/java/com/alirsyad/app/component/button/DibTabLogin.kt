package com.alirsyad.app.component.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentTabLoginBinding
import com.alirsyad.app.utils.setFontBold
import com.alirsyad.app.utils.setFontRegular

class DibTabLogin(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentTabLoginBinding = ComponentTabLoginBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setupTab(
        tabStudentSelected: () -> Unit,
        tabVisitorSelected: () -> Unit,
    ) = with(binding){
        clStudent.setOnClickListener {
            tabStudentSelected.invoke()
            setTabStudent()
        }

        clVisitor.setOnClickListener {
            tabVisitorSelected.invoke()
            setTabVisitor()
        }
    }

    fun setTabStudent() = with(binding){
        clStudent.background = ContextCompat.getDrawable(context, R.drawable.bg_tab_login_selected)
        viewStudent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentDark))
        tvTabStudent.setTextColor(ContextCompat.getColor(context, R.color.colorAccentDark))
        tvTabStudent.setFontBold()

        clVisitor.background = ContextCompat.getDrawable(context, R.drawable.bg_tab_login)
        viewVisitor.setBackgroundColor(ContextCompat.getColor(context, R.color.neutral_50))
        tvTabVisitor.setTextColor(ContextCompat.getColor(context, R.color.neutral_50))
        tvTabVisitor.setFontRegular()
    }

    fun setTabVisitor() = with(binding){
        clStudent.background = ContextCompat.getDrawable(context, R.drawable.bg_tab_login)
        viewStudent.setBackgroundColor(ContextCompat.getColor(context, R.color.neutral_50))
        tvTabStudent.setTextColor(ContextCompat.getColor(context, R.color.neutral_50))
        tvTabStudent.setFontRegular()

        clVisitor.background = ContextCompat.getDrawable(context, R.drawable.bg_tab_login_selected)
        viewVisitor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentDark))
        tvTabVisitor.setTextColor(ContextCompat.getColor(context, R.color.colorAccentDark))
        tvTabVisitor.setFontBold()
    }
}