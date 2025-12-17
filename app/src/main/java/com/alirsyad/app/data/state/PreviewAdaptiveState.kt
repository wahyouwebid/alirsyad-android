package com.alirsyad.app.data.state

import com.alirsyad.app.data.model.previewadaptive.PreviewAdaptiveResponse

sealed class PreviewAdaptiveState {
    object Loading : PreviewAdaptiveState()
    data class Result(val data: PreviewAdaptiveResponse) : PreviewAdaptiveState()
    data class Error(val error: Throwable) : PreviewAdaptiveState()
}