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
import com.alirsyad.app.data.model.simulation.Simulasi
import com.alirsyad.app.databinding.AdapterLearningSimulationBinding
import com.alirsyad.app.utils.ErrorType
import com.alirsyad.app.utils.hide
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class LearningSimulationAdapter(
    private val context: AppCompatActivity,
    private val isPengunjung: Int,
    private val showDetail: (Simulasi) -> Unit
) : RecyclerView.Adapter<LearningSimulationAdapter.ViewHolder>() {

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
            if (item.rataRataScore.toInt() != 0) {
                pbYourScore.progress = item.rataRataScore.toFloat()
                tvScore.text = String.format("${item.rataRataScore.toFloat().roundToInt()}/100")
            } else {
                pbYourScore.hide(true)
                tvScore.hide(true)
                tvLabelYourScore.hide(true)
            }
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
                        tvLabelYourScore.setTextColor(ContextCompat.getColor(root.context, R.color.grey_400))
                        tvScore.setTextColor(ContextCompat.getColor(root.context, R.color.state_color_blue_light2))
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
        AdapterLearningSimulationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLearningSimulationBinding) :
        RecyclerView.ViewHolder(view.root)

}