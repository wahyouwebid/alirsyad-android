package com.alirsyad.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.component.customview.DibBottomSheet
import com.alirsyad.app.data.model.video.DataVideo
import com.alirsyad.app.databinding.AdapterLearningVideoBinding
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.hide
import com.squareup.picasso.Picasso

class LearningVideoAdapter(
    private val context: AppCompatActivity,
    private val isPengunjung: Int,
    private val showDetail: (DataVideo) -> Unit
) : RecyclerView.Adapter<LearningVideoAdapter.ViewHolder>() {

    private var data = ArrayList<DataVideo>()

    fun setData(itemList: List<DataVideo>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.name
            val url = BuildConfig.imageUrl + item.icon
            Picasso.get().load(url).into(ivThumbnail)
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

            if (position == data.size -1) {
                viewStroke.hide()
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterLearningVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLearningVideoBinding) : RecyclerView.ViewHolder(view.root)

}