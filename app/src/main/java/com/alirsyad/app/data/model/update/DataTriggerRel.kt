package com.alirsyad.app.data.model.update

import com.google.gson.annotations.SerializedName

data class DataTriggerRel(
    @SerializedName("id") var id: Int? = -1,
    @SerializedName("name") var name: String? = "",
    @SerializedName("pdf_path") var pdfPath: String? = "",
    @SerializedName("description") var description: String? = "",
    @SerializedName("icon") var icon: String? = "",
    @SerializedName("mata_pelajaran_id") var mataPelajaranId: Int? = -1,
    @SerializedName("uploader_id") var uploaderId: Int? = -1,
    @SerializedName("created_at") var createdAt: String? = "",
    @SerializedName("updated_at") var updatedAt: String? = "",
    @SerializedName("deleted_at") var deletedAt: String? = "",
    @SerializedName("slug") var slug: String? = "",
    @SerializedName("semester") var semester: Int? = -1,
    @SerializedName("tahun_ajaran") var tahunAjaran: String? = "",
    @SerializedName("urutan") var urutan: Int? = -1,
    @SerializedName("read") var read: Boolean? = false,
    @SerializedName("pdf_url") var pdfUrl: String? = "",
    @SerializedName("previous") var previous: DataPrevious? = DataPrevious()
)