package com.alirsyad.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.courses.DataCourse
import com.alirsyad.app.databinding.AdapterCoursesInprogressGridBinding

class SubjectCourseAdapter(
    private val isHome: Boolean = false,
    private val showDetail: (DataCourse) -> Unit,
    private val showMore: (DataCourse) -> Unit
): RecyclerView.Adapter<SubjectCourseAdapter.ViewHolder>() {

    private var data = ArrayList<DataCourse>()

    fun setData(itemList: List<DataCourse>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            ivIcon.load(BuildConfig.imageUrl + item.icon) {
                placeholder(R.drawable.bg_placeholder_square_menu)
                error(R.drawable.bg_placeholder_square_menu)
            }
            tvName.text = item.name
            if (isHome && position == 7) {
                tvName.text = root.context.getString(R.string.title_menu_more)
                ivIcon.load(R.drawable.ic_more_menu) {
                    placeholder(R.drawable.bg_placeholder_square_menu)
                }
                root.setOnClickListener {
                    showMore(item)
                }
            } else {
                root.setOnClickListener {
                    showDetail(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterCoursesInprogressGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterCoursesInprogressGridBinding) : RecyclerView.ViewHolder(view.root)

}