package com.alirsyad.app.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.Html
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import com.alirsyad.app.R
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun File.toMultiPartBody(key: String): MultipartBody.Part {
    val requestFile = this.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData(key, this.name, requestFile)
    return body
}

fun View.hide(gone: Boolean = false) {
    visibility = if (gone) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Int.ordinal() = "$this" + when {
    (this % 100 in 11..13) -> "th"
    (this % 10) == 1 -> "st"
    (this % 10) == 2 -> "nd"
    (this % 10) == 3 -> "rd"
    else -> "th"
}

fun TextView.setFontBold() {
    this.typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
}

fun TextView.setFontRegular() {
    this.typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPhoneNumber() =
    isNotEmpty() && this.length >= 8 && this.length <=13

fun String.isValidPassword() =
    isNotEmpty() && this.length >= 8

fun String.isValidPasswordNotMatch(password: String) =
    isNotEmpty() && this == password


fun TextInputEditText.setMaxLength(length: Int) {
    val filterArray = arrayOfNulls<InputFilter>(1)
    filterArray[0] = InputFilter.LengthFilter(length)
    this.filters = filterArray
}

fun String?.checkEmpty(): String =
    if (this.isNullOrEmpty() || this.isBlank()) "-" else this

fun View.setOnSingleClickListener(action: () -> Unit) {
    this.setOnClickListener { view ->
        synchronized(view) {
            view.isEnabled = false
            view.postDelayed({ view.isEnabled = true }, 500L)
            action()
        }
    }
}

fun String.dateFormat(
    sourcePattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    targetPattern: String = "dd MMM yyyy",
    locale: Locale = Locale.getDefault(),
): String {
    val simpleFormat = SimpleDateFormat(sourcePattern, Locale.ENGLISH)
    val dateFormat = simpleFormat.parse(this)
    val calendar = Calendar.getInstance()
    if (dateFormat != null) {
        calendar.time = dateFormat
    }
    val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    val date = SimpleDateFormat(sourcePattern, locale).parse(this)!!
    return "${dayOfWeek}, ${SimpleDateFormat(targetPattern, locale).format(date)}"
}

fun String.readHtml(): Spanned {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
}

fun Context.loadJSONFromAssets(fileName: String): String {
    return applicationContext.assets.open(fileName).bufferedReader().use { reader ->
        reader.readText()
    }
}

fun Context.openDownloadedPDF(fileName: String) {
    val file =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
    val path: Uri =
        FileProvider.getUriForFile(
            this.applicationContext,
            this.applicationContext.packageName + ".provider",
            file
        )
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(path, "application/pdf")
    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
    val chooserIntent = Intent.createChooser(intent, "Open with")
    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        this.startActivity(chooserIntent)
    } catch (e: ActivityNotFoundException) {
        Log.e("TAG", "Failed to open PDF  ${e.localizedMessage}")
    }
}