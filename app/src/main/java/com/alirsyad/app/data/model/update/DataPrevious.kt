package com.alirsyad.app.data.model.update

import com.google.gson.annotations.SerializedName

class DataPrevious(
    @SerializedName("id") var id: Int? = -1,
    @SerializedName("name") var name: String? = "",
    @SerializedName("url") var url: String? = "",
    @SerializedName("slug_url") var slugUrl: String? = "",
    @SerializedName("endpoint") var endpoint: String? = ""
)