package com.alirsyad.app.ui.ereportscore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.databinding.AdapterAllSubjectsCarouselBinding
import com.alirsyad.app.utils.setFontBold
import com.alirsyad.app.utils.setFontRegular

class EReportCourseAdapter(
    position: Int,
    private val showCourses: (DataCourse) -> Unit,
) : RecyclerView.Adapter<EReportCourseAdapter.ViewHolder>() {

    private var data = ArrayList<DataCourse>()
    private var classPosition = position

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList: List<DataCourse>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    fun setPosition(position: Int) {
        classPosition = position
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTingkat.text = item.name

            root.setOnClickListener {
                classPosition = position
                showCourses(item)
                notifyDataSetChanged()
            }

            if (position == classPosition) {
                root.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_selected_rounded)
                tvTingkat.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                tvTingkat.setFontBold()
            } else {
                root.background = ContextCompat.getDrawable(root.context, R.drawable.bg_card_neutral_rounded)
                tvTingkat.setTextColor(ContextCompat.getColor(root.context, R.color.neutral_60))
                tvTingkat.setFontRegular()
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterAllSubjectsCarouselBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    class ViewHolder(val view: AdapterAllSubjectsCarouselBinding) :
        RecyclerView.ViewHolder(view.root)

}