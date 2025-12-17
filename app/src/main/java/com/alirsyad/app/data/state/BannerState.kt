package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.banner.ResponseBanner

sealed class BannerState {
    object Loading : BannerState()
    data class Result(val data: ResponseBanner) : BannerState()
    data class Error(val error: Throwable) : BannerState()
}