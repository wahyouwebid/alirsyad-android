package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.data.model.courses.Tingkat
import com.alirsyad.app.databinding.AdapterSubjectClassTkBinding
import com.alirsyad.app.ui.MainViewModel
import com.alirsyad.app.utils.setFontBold
import com.alirsyad.app.utils.setFontRegular

class SubjectClassTKAdapter(
    private val viewModel: MainViewModel,
    private val showCourses: (Tingkat) -> Unit
) : RecyclerView.Adapter<SubjectClassTKAdapter.ViewHolder>() {

    private var data = ArrayList<Tingkat>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList: List<Tingkat>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        with(holder.view) {
            val item = data[position]
            val classPosition = viewModel.getClassPosition()

            tvTingkat.text = String.format("${item.jenjang.name} ${item.name}")

            root.setOnClickListener {
                showCourses(item)
                viewModel.setClassPosition(position)
                viewModel.setClassId(item.id)
                viewModel.setIsTab(true)
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
        AdapterSubjectClassTkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    class ViewHolder(val view: AdapterSubjectClassTkBinding) :
        RecyclerView.ViewHolder(view.root)

}