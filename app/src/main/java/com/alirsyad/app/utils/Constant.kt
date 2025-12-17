package com.alirsyad.app.utils

object Constant {
    const val Bearer = "Bearer "
    const val TRIGGER_VIDEO = "video"
    const val SUCCESS_SENDING_EMAIL = "SUCCESS_SENDING_EMAIL"
    const val SUCCESS_ACCOUNT_VERIFIED = "SUCCESS_ACCOUNT_VERIFIED"
    const val SUCCESS_NIS_FORGOT_PASSWORD = "SUCCESS_NIS_FORGOT_PASSWORD"
    const val SUCCESS_EMAIL_FORGOT_PASSWORD = "SUCCESS_EMAIL_FORGOT_PASSWORD"
    const val USER_TYPE = "USER_TYPE"
    const val STUDENT = "STUDENT"
    const val VISITOR = "VISITOR"
    const val EMPTY_STRING = ""
    const val ROLE = "ROLE"

    object Intent {
        const val DATA = "data"
        const val ID = "id"
        const val NAME = "name"
        const val MATA_PELAJARAN_ID = "mata_pelajaran_id"
        const val DATA_MODULE = "data_module"
        const val DATA_QUESTION = "data_question"
        const val CHECK_ANSWER = "CHECK_ANSWER"
        const val PREVIEW_REQUEST = "PREVIEW_R_REQUEST"
        const val PREVIEW_NEXT_ID = "PREVIEW_NEXT_ID"
        const val URL_YOUTUBE = "URL_YOUTUBE"
    }

    object Status {
        const val PASS = "pass"
        const val FAIL = "fail"
    }

    object Level {
        const val EASY = "mudah"
        const val MEDIUM = "sedang"
        const val HARD = "sulit"
    }

    object Role {
        const val STUDENT = "student"
        const val STUDENT_INT = 0
        const val VISITOR = "visitor"
        const val VISITOR_INT = 1
    }

    object ButtonType {
        const val PRIMARY = "primary"
        const val OUTLINED = "outline"
    }

    object LoadingType {
        const val TRANSPARENT = "transparent"
        const val WITH_BACKGROUND = "with_background"
    }

    object VisitorStatus {
        const val AKTIF = "AKTIF"
        const val BELUM_DIKONFIRMASI = "BELUM_DIKONFIRMASI"
        const val TIDAK_AKTIF = "TIDAK_AKTIF"
    }

    object ValueChart {
        const val LOW_VALUE = 0.02f
        const val HIGH_VALUE = 2f

    }

}