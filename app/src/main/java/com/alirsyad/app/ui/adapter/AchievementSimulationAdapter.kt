package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.data.model.achievement.Simulasi
import com.alirsyad.app.databinding.AdapterAchievementBinding
import com.alirsyad.app.utils.checkEmpty
import com.alirsyad.app.utils.dateFormat
import com.alirsyad.app.utils.hide
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.math.roundToInt

class AchievementSimulationAdapter(
    private val showDetail: (Simulasi) -> Unit
) : RecyclerView.Adapter<AchievementSimulationAdapter.ViewHolder>() {

    private var data = ArrayList<Simulasi>()

    fun setData(itemList: List<Simulasi>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.name
            tvDate.text = if (item.lastScore == null) {
                item.updatedAt?.dateFormat().checkEmpty()
            } else {
                item.lastScore.updatedAt?.dateFormat().checkEmpty()
            }
            if (item.rataRataScore.toInt() != 0) {
                pbProgress.progress = item.rataRataScore.toFloat()
                tvProgressValue.text = String.format("${item.rataRataScore.toFloat().roundToInt()}/100")
            } else {
                pbProgress.hide(true)
                tvLabelProgress.hide(true)
                tvProgressValue.hide(true)

            }
            Glide.with(ivThumbnail)
                .load(item.coverUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.bg_placeholder)
                .into(ivThumbnail)

            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterAchievementBinding) : RecyclerView.ViewHolder(view.root)

}