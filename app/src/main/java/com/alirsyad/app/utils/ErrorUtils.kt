package com.alirsyad.app.utils

import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.forgot.error.ResponseErrorForgotEmail
import com.alirsyad.app.data.model.forgot.error.ResponseErrorForgotNis
import com.alirsyad.app.data.model.register.error.ResponseErrorRegistered
import com.alirsyad.app.data.model.resetpassword.error.ResponseErrorResetPassword
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

object ErrorUtils {
    fun setupError(
        activity: AppCompatActivity,
        error: Throwable,
        showBottomSheet: () -> Unit,
        tryAgain: (() -> Unit?)? = null
    ) {
        try {
            when (error) {
                is HttpException -> {
                    if (error.code() == 500) {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, activity,
                            onClickOk = {
                                tryAgain?.invoke()
                            }).show(activity.supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    } else {
                        showBottomSheet.invoke()
                    }
                }
                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, activity,
                        onClickCancel = {
                            tryAgain?.invoke()
                        },
                        onClickOk = {
                            activity.startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                    ).show(activity.supportFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }
            }
        } catch (e : Exception){
            DibBottomSheet(ErrorType.ERROR_SYSTEM.name, activity,
                onClickOk = {
                    tryAgain?.invoke()
                }
            ).show(activity.supportFragmentManager, ErrorType.ERROR_SYSTEM.name)
        }
    }

    fun setupError(
        fragment: Fragment,
        error: Throwable,
        showBottomSheet: () -> Unit,
        tryAgain: (() -> Unit?)? = null
    ) {
        try {
            when (error) {
                is HttpException -> {
                    if (error.code() == 500) {
                        DibBottomSheet(ErrorType.ERROR_SYSTEM.name, fragment.requireContext(),
                            onClickOk = {
                                tryAgain?.invoke()
                            }).show(fragment.childFragmentManager, ErrorType.ERROR_SYSTEM.name)
                    } else {
                        showBottomSheet.invoke()
                    }
                }
                is IOException -> {
                    DibBottomSheet(ErrorType.ERROR_CONNECTION.name, fragment.requireContext(),
                        onClickCancel = {
                            tryAgain?.invoke()
                        },
                        onClickOk = {
                            fragment.startActivity(Intent(Settings.ACTION_SETTINGS))
                        }
                    ).show(fragment.childFragmentManager, ErrorType.ERROR_CONNECTION.name)
                }
            }
        } catch (e : Exception){
            DibBottomSheet(ErrorType.ERROR_SYSTEM.name, fragment.requireContext(),
                onClickOk = {
                    tryAgain?.invoke()
                }).show(fragment.childFragmentManager, ErrorType.ERROR_SYSTEM.name)
        }
    }

    fun responseErrorRegister(response: HttpException): ResponseErrorRegistered? {
        val errorBody = response.response()?.errorBody()?.string()
        return Gson().fromJson(errorBody, ResponseErrorRegistered::class.java)
    }

    fun responseErrorForgotNis(response: HttpException): ResponseErrorForgotNis? {
        val errorBody = response.response()?.errorBody()?.string()
        return Gson().fromJson(errorBody, ResponseErrorForgotNis::class.java)
    }

    fun responseErrorForgotEmail(response: HttpException): ResponseErrorForgotEmail? {
        val errorBody = response.response()?.errorBody()?.string()
        return Gson().fromJson(errorBody, ResponseErrorForgotEmail::class.java)
    }

    fun responseErrorResetPassword(response: HttpException): ResponseErrorResetPassword? {
        val errorBody = response.response()?.errorBody()?.string()
        return Gson().fromJson(errorBody, ResponseErrorResetPassword::class.java)
    }
}