package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.databinding.AdapterCourseByTingkatBinding

class CourseByTingkatAdapter(
    private val jenjang: String,
    private val showDetail: (DataCourse) -> Unit
) : RecyclerView.Adapter<CourseByTingkatAdapter.ViewHolder>() {

    private var data = ArrayList<DataCourse>()

    fun setData(itemList: List<DataCourse>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.name
            tvDescription.text = "Kelas ${item.tingkat?.name} $jenjang"
            ivThumbnail.load(BuildConfig.imageUrl + item.icon)
            btnContinue.setOnClickListener {
                showDetail(data[position])
            }
            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterCourseByTingkatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterCourseByTingkatBinding) : RecyclerView.ViewHolder(view.root)

}