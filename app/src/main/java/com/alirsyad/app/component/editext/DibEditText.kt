package com.alirsyad.app.component.editext

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.ComponentEditTextBinding
import com.alirsyad.app.utils.Utils.specialCharacterHandler
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.hideKeyboard
import com.alirsyad.app.utils.setFontRegular
import com.alirsyad.app.utils.setMaxLength
import com.alirsyad.app.utils.show


class DibEditText(
    context: Context,
    attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet){
    private var titleHeader: String? = null
    private var titleHint: String? = null
    private var titleError: String? = null
    private var isPassword: Boolean = false
    private var inputType: Int? = null
    private var maxLength: Int? = null

    private val binding: ComponentEditTextBinding = ComponentEditTextBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibEditText,
                0,
                0
            ).apply {
                try {
                    titleHeader = getString(R.styleable.DibEditText_titleHeader)
                    titleHint = getString(R.styleable.DibEditText_titleHint)
                    titleError = getString(R.styleable.DibEditText_titleError)
                    isPassword = getBoolean(R.styleable.DibEditText_isPassword, false)
                    inputType = getInt(R.styleable.DibEditText_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
                    maxLength = getInt(R.styleable.DibEditText_android_maxLength, 30)
                } finally {
                    setOptions()
                    setListener()
                    setDefault(titleHeader, titleHint, titleError, isPassword)
                    recycle()
                }
            }
        }
    }

    fun validateForm() = with(binding) {
        if (etInput.text.isNullOrEmpty()) {
            tvError.show()
            setEditTextError()
        } else {
            tvError.hide(true)
            setEditTextSuccess()
        }
    }

    fun setError(message: String) = with(binding) {
        tvError.show()
        tvError.text = message
        setEditTextError()
    }

    fun formNotEmpty() : Boolean = with(binding){
        return !(etInput.text.isNullOrEmpty() || etInput.text.isNullOrBlank())
    }

    private fun setDefault(
        header: String? = null,
        hint: String? = null,
        error: String? = null,
        isPassword: Boolean
    ) = with(binding) {
        tvTitle.text = header
        etInput.hint = hint
        tvError.text = if (error.isNullOrEmpty()) context.getText(R.string.title_edit_text_error) else error
        setValidatePassword(isPassword)
    }

    private fun setListener() = with(binding){
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not Implemented
            }

            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                t?.let {

                }
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (inputType == EditorInfo.TYPE_CLASS_PHONE ||
                        inputType == EditorInfo.TYPE_CLASS_NUMBER
                    ) {
                        handleSpecialCase(s, this)
                    }
                    if (it.isNotEmpty()) {
                        setEditTextSuccess()
                        tvError.hide(true)
                    } else {
                        setEditTextError()
                        tvError.show()
                    }
                }
            }
        })

        etInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                etInput.clearFocus()
                etInput.hideKeyboard()
            }
            false
        }

        maxLength?.let { etInput.setMaxLength(it) }
    }

    override fun dispatchKeyEventPreIme(event: KeyEvent?): Boolean {
        event?.let {
            with(binding){
                if (it.action== KeyEvent.ACTION_UP &&
                    it.keyCode == KeyEvent.KEYCODE_BACK &&
                    etInput.isFocused)
                {
                    etInput.clearFocus()
                    etInput.hideKeyboard()
                }
            }
        }
        return super.dispatchKeyEventPreIme(event)
    }

    private fun setValidatePassword(isPassword: Boolean) = with(binding) {
        tlTextInput.isPasswordVisibilityToggleEnabled = isPassword
    }

    private fun setOptions() = with(binding) {
        inputType?.let { etInput.inputType = it }
        etInput.setFontRegular()
    }

    private fun handleSpecialCase(
        s: Editable,
        watcher: TextWatcher
    ) = with(binding) {
        etInput.removeTextChangedListener(watcher)
        specialCharacterHandler(
            s,
            isTypeNumber()
        ).let { text ->
            etInput.apply {
                setText(text)
                val selectionIndex = if (text.length < this.length()) text.length else this.length()
                setSelection(selectionIndex)
            }
        }
        etInput.addTextChangedListener(watcher)
    }


    private fun isTypeNumber(): Boolean {
        with(binding) {
            return etInput.inputType == InputType.TYPE_CLASS_PHONE
                    || etInput.inputType == InputType.TYPE_CLASS_NUMBER
        }
    }

    private fun setEditTextError() = with(binding) {
        etInput.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_red)
    }

    private fun setEditTextSuccess() = with(binding) {
        etInput.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_grey)
    }

    fun getString() = binding.etInput.text.toString()

}