package com.alirsyad.app.component.customview

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.alirsyad.app.R
import com.alirsyad.app.databinding.PopupDibSubmittedFailedBinding
import com.alirsyad.app.databinding.PopupDibSubmittedIsEmptyBinding
import com.alirsyad.app.databinding.PopupDibSubmittedSuccessBinding
import com.alirsyad.app.utils.Constant

class DibPopUp(private val activity: AppCompatActivity) : Dialog(activity) {

    fun popupSubmittedSuccess(
        score: String?,
        level: String?,
        totalQuestion: Int? = 0,
        onClickCancel:() -> Unit,
        onClickOk:() -> Unit,
        onClickCheckStudy:() -> Unit,
    ): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding: PopupDibSubmittedSuccessBinding by lazy {
            PopupDibSubmittedSuccessBinding.inflate(layoutInflater)
        }
        dialog.setContentView(binding.root)
        val window: Window? = dialog.window
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            tvYourScore.text = String.format("${score}/${totalQuestion}")
            tvLevel.text = when (level) {
                Constant.Level.EASY -> activity.getString(R.string.title_easy_level)
                Constant.Level.MEDIUM -> activity.getString(R.string.title_medium_level)
                Constant.Level.HARD -> activity.getString(R.string.title_hard_level)
                else -> activity.getString(R.string.title_hard_level)
            }
            btnCancel.setOnClickListener {
                onClickCancel.invoke()
                dialog.dismiss()
            }
            btnSubmit.setOnClickListener {
                onClickOk.invoke()
                dialog.dismiss()
            }
            tvCheckTheStudy.setOnClickListener {
                onClickCheckStudy.invoke()
                dialog.dismiss()
            }
        }

        dialog.show()
        return dialog
    }

    fun popupSubmittedFailed(
        score: String?,
        totalQuestion: Int? = 0,
        onClickCancel:() -> Unit,
        onClickOk:() -> Unit,
        onClickCheckStudy:() -> Unit,
    ): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding: PopupDibSubmittedFailedBinding by lazy {
            PopupDibSubmittedFailedBinding.inflate(layoutInflater)
        }
        dialog.setContentView(binding.root)
        val window: Window? = dialog.window
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            tvYourScore.text = String.format("${score}/${totalQuestion}")
            btnCancel.setOnClickListener {
                onClickCancel.invoke()
                dialog.dismiss()
            }
            btnSubmit.setOnClickListener {
                onClickOk.invoke()
                dialog.dismiss()
            }
            tvCheckTheStudy.setOnClickListener {
                onClickCheckStudy.invoke()
                dialog.dismiss()
            }
        }

        dialog.show()
        return dialog
    }

    fun popupSubmittedIsEmpty(): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding: PopupDibSubmittedIsEmptyBinding by lazy {
            PopupDibSubmittedIsEmptyBinding.inflate(layoutInflater)
        }
        dialog.setContentView(binding.root)
        val window: Window? = dialog.window
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            btnSubmit.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()
        return dialog
    }
}