package com.alirsyad.app.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.alirsyad.app.databinding.LayoutDialogBinding
import com.alirsyad.app.databinding.LayoutLoadingBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utils {

    fun alertDialog(activity: AppCompatActivity, title: String, message: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val binding: LayoutDialogBinding by lazy {
            LayoutDialogBinding.inflate(activity.layoutInflater)
        }

        dialog.setContentView(binding.root)

        with(binding) {
            tvTitle.text = title
            tvMessage.text = message
            btnOk.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()

        val window: Window? = dialog.window
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
    }

    fun windowDialog(dialog : Dialog){
        val window: Window? = dialog.window
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
    }

    fun windowFeature(dialog: Dialog){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
    }

    fun progressDialog(activity: AppCompatActivity): Dialog {
        val dialog = Dialog(activity)
        val binding: LayoutLoadingBinding by lazy {
            LayoutLoadingBinding.inflate(activity.layoutInflater)
        }
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    fun progressDialog(fragment: Fragment): Dialog {
        val dialog = Dialog(fragment.requireActivity())
        val binding: LayoutLoadingBinding by lazy {
            LayoutLoadingBinding.inflate(fragment.layoutInflater)
        }
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date : String) : String{
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd/MM/yyyy")
        var d: Date? = null

        try {
            d = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return output.format(d)
    }

    fun setupStatusBar(activity: AppCompatActivity) {
        activity.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        activity.window.statusBarColor = Color.TRANSPARENT
    }

    fun isNumeric(toCheck: String?): Boolean? {
        return toCheck?.all { char -> char.isDigit() }
    }

    fun setupSoftMode(activity: AppCompatActivity) {
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    fun specialCharacterHandler(editable: Editable?, isNumberOnly: Boolean = false): String {
        val stringText = editable.toString()
        return when (isNumberOnly) {
            true -> stringText.replace("[^\\d.]".toRegex(), "")
            else -> stringText.replace("[^A-Za-z\\d\\s]+".toRegex(), "")
        }
    }

    fun checkIsSpecialChar(char: String): Boolean {
        return if (char.length >= 8) {
            val letter: Pattern = Pattern.compile("[a-zA-z]")
            val digit: Pattern = Pattern.compile("[0-9]")
            val special: Pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
            val hasLetter: Matcher = letter.matcher(char)
            val hasDigit: Matcher = digit.matcher(char)
            val hasSpecial: Matcher = special.matcher(char)
            hasLetter.find() && hasDigit.find() && hasSpecial.find()
        } else false
    }
}