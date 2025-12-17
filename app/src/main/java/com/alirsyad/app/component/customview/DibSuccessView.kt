package com.alirsyad.app.component.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentSuccessViewBinding
import com.alirsyad.app.utils.Constant.SUCCESS_ACCOUNT_VERIFIED
import com.alirsyad.app.utils.Constant.SUCCESS_NIS_FORGOT_PASSWORD
import com.alirsyad.app.utils.Constant.SUCCESS_SENDING_EMAIL
import com.alirsyad.app.utils.show

class DibSuccessView(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){

    private val binding: ComponentSuccessViewBinding = ComponentSuccessViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setType(
        type: String?,
        onClick: (() -> Unit?)? = null
    ) {
        with(binding) {
            when(type) {
                SUCCESS_SENDING_EMAIL -> {
                    tvTitle.text = context.getString(R.string.title_success_sending_email)
                    tvSubTitle.text = context.getString(R.string.title_sub_success_sending_email)
                }

                SUCCESS_ACCOUNT_VERIFIED -> {
                    tvTitle.text = context.getString(R.string.title_success_account_verified)
                    tvSubTitle.text = context.getString(R.string.title_sub_success_account_verified)
                }

                SUCCESS_NIS_FORGOT_PASSWORD -> {
                    tvTitle.text = context.getString(R.string.title_success_nis_forgot_password)
                    tvSubTitle.text = context.getString(R.string.title_sub_success_forgot_password)
                }

                else -> {
                    clResend.show()
                    tvTitle.text = context.getString(R.string.title_success_email_forgot_password)
                    tvSubTitle.text = context.getString(R.string.title_sub_success_email_forgot_password)
                    tvResend.setOnClickListener {
                        onClick?.invoke()
                    }
                }
            }
        }
    }
}