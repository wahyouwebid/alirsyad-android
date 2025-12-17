package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.module.DataModule
import com.alirsyad.app.databinding.AdapterLearningModuleBinding
import com.alirsyad.app.utils.ErrorType
import com.bumptech.glide.Glide

class LearningModuleAdapter(
    private val context: AppCompatActivity,
    private val isPengunjung: Int,
    private val showDetail: (DataModule) -> Unit,
) : RecyclerView.Adapter<LearningModuleAdapter.ViewHolder>() {

    private var data = ArrayList<DataModule>()

    fun setData(itemList: List<DataModule>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.name
            Glide.with(ivThumbnail)
                .load(BuildConfig.imageUrl + item.icon)
                .into(ivThumbnail)
            if (isPengunjung == 1) {
                if (item.mapelAssigned == 1) {
                    root.setOnClickListener {
                        showDetail(data[position])
                    }
                } else {
                    if (item.isPublic == 1) {
                        tvTitle.setTextColor(ContextCompat.getColor(root.context, R.color.grey_900))
                        root.setOnClickListener {
                            showDetail(data[position])
                        }
                    } else {
                        tvTitle.setTextColor(ContextCompat.getColor(root.context, R.color.grey_400))
                        root.setOnClickListener {
                            DibBottomSheet(ErrorType.ERROR_IS_VISITOR.name, context).show(context.supportFragmentManager, ErrorType.ERROR_IS_VISITOR.name)
                        }
                    }
                }
            } else {
                root.setOnClickListener {
                    showDetail(data[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterLearningModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLearningModuleBinding) : RecyclerView.ViewHolder(view.root)

}