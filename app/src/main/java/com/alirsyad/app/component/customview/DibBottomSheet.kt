package com.alirsyad.app.component.customview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alirsyad.app.R
import com.alirsyad.app.databinding.BottomSheetErrorBinding
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.hide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DibBottomSheet(
    private val type: String,
    private val appContext: Context,
    private val message: String = "",
    private val onClickCancel: (() -> Unit?)? = null,
    private val onClickOk: (() -> Unit?)? = null,
) : BottomSheetDialogFragment() {

    private val binding: BottomSheetErrorBinding by lazy {
        BottomSheetErrorBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() = with(appContext) {
        when(type) {
            ErrorType.ERROR_CONNECTION.name -> {
                setData(
                    getString(R.string.title_error_internet_connection),
                    getString(R.string.title_error_message_internet_connection),
                    R.drawable.ic_error_connection,
                    getString(R.string.title_btn_error_try_again),
                    getString(R.string.title_btn_error_go_to_settings),
                )
            }

            ErrorType.ERROR_TECHNICAL.name -> {
                setData(
                    getString(R.string.title_error_technical),
                    getString(R.string.title_error_message_technical),
                    R.drawable.ic_error_fail_technical,
                    getString(R.string.title_btn_error_close_app),
                    getString(R.string.title_btn_error_contact_cs),
                )
            }

            ErrorType.ERROR_SYSTEM.name -> {
                setData(
                    getString(R.string.title_error_system_error),
                    getString(R.string.title_error_system_error_message),
                    R.drawable.ic_error_fail_system,
                    getString(R.string.title_btn_error_close_app),
                    getString(R.string.title_btn_error_try_again),
                )
            }

            ErrorType.LOGOUT.name -> {
                setData(
                    getString(R.string.title_error_logout),
                    getString(R.string.title_error_logout_message),
                    R.drawable.ic_sure_logout,
                    getString(R.string.title_btn_yes_sure),
                    getString(R.string.title_btn_no_im_not),
                )
            }

            ErrorType.ERROR_REGISTRATION.name -> {
                setData(
                    getString(R.string.title_error_please_check_data_again),
                    getString(R.string.title_error_please_check_data_again_message),
                    R.drawable.ic_error_input,
                    getString(R.string.title_btn_no_im_sure),
                    getString(R.string.title_btn_check_again),
                )
            }

            ErrorType.ERROR_WAITING_RESET_PASSWORD.name -> {
                setData(
                    getString(R.string.title_error_login_failed),
                    getString(R.string.title_error_waiting_reset_password_message),
                    R.drawable.ic_error_login
                )
            }

            ErrorType.UPDATE_VERSION_READY.name -> {
                setData(
                    getString(R.string.title_update_version),
                    getString(R.string.title_update_version_message),
                    R.drawable.ic_update_version,
                    getString(R.string.title_btn_error_later),
                    getString(R.string.title_btn_error_lets_update),
                )
            }

            ErrorType.ERROR_LOGIN.name -> {
                setData(
                    getString(R.string.title_error_login_failed),
                    message,
                    R.drawable.ic_error_login
                )
            }

            ErrorType.ERROR_IS_VISITOR.name -> {
                setData(
                    getString(R.string.title_error_access_denied),
                    getString(R.string.title_error_message_access_denied),
                    R.drawable.ic_error_no_data,
                    titleBtnOk =  getString(R.string.title_btn_okkay),
                )
            }

            else -> {
                setData(
                    getString(R.string.title_error_proccess_data),
                    getString(R.string.title_error_message_proccess_data),
                    R.drawable.ic_error_no_data,
                    titleBtnOk =  getString(R.string.title_btn_error_try_again),
                )
            }
        }
    }

    private fun setData(
        titleMessage: String,
        contentMessage: String,
        image: Int,
        titleBtnCancel: String? = null,
        titleBtnOk: String? = null,
    ) = with(binding) {
        if (message.isEmpty()) {
            tvTitle.text = titleMessage
            tvMessage.text = contentMessage
            ivError.background = ContextCompat.getDrawable(appContext, image)
            setButton(titleBtnCancel, titleBtnOk)
        } else {
            tvTitle.text = titleMessage
            tvMessage.text = message
            ivError.background = ContextCompat.getDrawable(appContext, image)
            setButton(titleBtnCancel, titleBtnOk)
        }
    }

    private fun setButton(
        titleBtnCancel: String? = null,
        titleBtnOk: String? = null,
    ) = with(binding){
        when {
            titleBtnCancel.isNullOrEmpty() && titleBtnOk.isNullOrEmpty() -> {
                btnCancel.hide(true)
                btnOk.hide(true)
            }

            titleBtnCancel.isNullOrEmpty() -> {
                btnCancel.hide(true)
                btnOk.text = titleBtnOk
                btnOk.setOnClickListener {
                    onClickOk?.invoke()
                    dismiss()
                }
            }

            else -> {
                btnCancel.text = titleBtnCancel
                btnOk.text = titleBtnOk
                btnCancel.setOnClickListener {
                    onClickCancel?.invoke()
                    dismiss()
                }
                btnOk.setOnClickListener {
                    onClickOk?.invoke()
                    if (titleBtnOk != getString(R.string.title_btn_error_go_to_settings)) {
                        dismiss()
                    }
                }

            }
        }
    }
}