package com.alirsyad.app.data.model.update

import com.google.gson.annotations.SerializedName

data class DataUpdate(
    @SerializedName("id") var id: Int? = -1,
    @SerializedName("trigger_event") var triggerEvent: String? = "",
    @SerializedName("trigger") var trigger: String? = "",
    @SerializedName("trigger_id") var triggerId: Int = -1,
    @SerializedName("trigger_name") var triggerName: String = "",
    @SerializedName("mata_pelajaran") var mataPelajaran: String = "",
    @SerializedName("created_at") var createdAt: String? = "",
    @SerializedName("updated_at") var updatedAt: String? = "",
    @SerializedName("tingkat_id") var tingkatId: Int? = -1,
    @SerializedName("mata_pelajaran_id") var mataPelajaranId: Int? = -1,
    @SerializedName("logo") var logo: String? = "",
    @SerializedName("trigger_rel") var triggerRel: DataTriggerRel? = DataTriggerRel()
)