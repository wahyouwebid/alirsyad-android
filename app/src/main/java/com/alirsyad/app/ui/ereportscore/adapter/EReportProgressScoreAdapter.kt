package com.alirsyad.app.ui.ereportscore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.data.model.ereport.Nilai
import com.alirsyad.app.databinding.AdapterEreportProgressScoreEasyBinding
import com.alirsyad.app.databinding.AdapterEreportProgressScoreHardBinding
import com.alirsyad.app.databinding.AdapterEreportProgressScoreMediumBinding

class EReportProgressScoreAdapter(
    private val appContext: Context?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context : Context? = null
    private var data = ArrayList<Nilai>()

    companion object {
        const val EASY = 1
        const val MEDIUM = 2
    }

    fun setData(itemList: List<Nilai>) {
        context = appContext
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = levelMapper(data[position].tingkatKesulitan)
        val item = data[position]
        val question = String.format("${item.totalBenar}/${item.totalTerjawab}")
        val percentage = item.percentage?.toFloat()
        when(viewType) {
            EASY -> {
                val binding = holder as ViewHolderEasy
                if (percentage != null) {
                    binding.view.pbProgress.progress = percentage
                }
                binding.view.tvQuestions.text = question
            }

            MEDIUM -> {
                val binding = holder as ViewHolderMedium
                if (percentage != null) {
                    binding.view.pbProgress.progress = percentage
                }
                binding.view.tvQuestions.text = question
            }

            else -> {
                val binding = holder as ViewHolderHard
                if (percentage != null) {
                    binding.view.pbProgress.progress = percentage
                }
                binding.view.tvQuestions.text = question
            }
        }
    }

    private fun levelMapper(level: String?): Int {
        return when(level) {
            "mudah" -> 1
            "sedang" -> 2
            else -> 3
        }
    }

    override fun getItemViewType(position: Int): Int = levelMapper(data[position].tingkatKesulitan)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            EASY -> ViewHolderEasy(
                AdapterEreportProgressScoreEasyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            MEDIUM -> ViewHolderMedium(
                AdapterEreportProgressScoreMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> ViewHolderHard(
                AdapterEreportProgressScoreHardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    class ViewHolderEasy(val view: AdapterEreportProgressScoreEasyBinding) : RecyclerView.ViewHolder(view.root)

    class ViewHolderMedium(val view: AdapterEreportProgressScoreMediumBinding) : RecyclerView.ViewHolder(view.root)

    class ViewHolderHard(val view: AdapterEreportProgressScoreHardBinding) : RecyclerView.ViewHolder(view.root)

}