package com.alirsyad.app.ui.main.ereport

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.ereport.EReportCurrentScore
import com.alirsyad.app.databinding.AdapterEreportYourScoreBinding
import com.alirsyad.app.utils.Constant.Level.EASY
import com.alirsyad.app.utils.Constant.Level.MEDIUM
import com.alirsyad.app.utils.dateFormat
import com.bumptech.glide.Glide

class EReportAdapter(
    private val showDetail: (EReportCurrentScore) -> Unit
) : RecyclerView.Adapter<EReportAdapter.ViewHolder>() {

    private var data = ArrayList<EReportCurrentScore>()

    fun setData(itemList: List<EReportCurrentScore>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvSubjectName.text = item.mataPelajaranName
            tvModuleName.text = item.title
            tvScore1.text = item.totalBenar.toString()
            tvScore2.text = item.totalTerjawab.toString()
            tvDate.text = item.lastUpdated?.dateFormat()
            tvLevel.getLevel(item.tingkatKesulitan)

            Glide.with(ivSubject)
                .load(BuildConfig.imageUrl + item.mataPelajaranIcon)
                .error(R.drawable.bg_placeholder)
                .into(ivSubject)

            root.setOnClickListener {
                showDetail.invoke(item)
            }
        }
    }

     private  fun TextView.getLevel(level: String?){ when(level) {
            EASY -> {
                this.text = this.context.getString(R.string.title_easy_level)
                this.setTextColor(ContextCompat.getColor(context, R.color.state_color_green))
            }
            MEDIUM -> {
                this.text = this.context.getString(R.string.title_medium_level)
                this.setTextColor(ContextCompat.getColor(context, R.color.state_color_yellow))
            }
            else -> {
                this.text = this.context.getString(R.string.title_hard_level)
                this.setTextColor(ContextCompat.getColor(context, R.color.state_color_red))
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterEreportYourScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterEreportYourScoreBinding) : RecyclerView.ViewHolder(view.root)

}