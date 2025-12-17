package com.alirsyad.app.ui.ereportscore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.ereport.EReportScore
import com.alirsyad.app.data.model.ereport.Subbab
import com.alirsyad.app.databinding.AdapterLearningAdaptiveBinding
import com.bumptech.glide.Glide

class EReportModuleAdapter(
    private val appContext: Context,
) : RecyclerView.Adapter<EReportModuleAdapter.ViewHolder>() {

    private var context : Context? = null
    private var data = ArrayList<EReportScore>()


    fun setData(itemList: List<EReportScore>) {
        context = appContext
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.nama
            Glide.with(ivThumbnail)
                .load(BuildConfig.imageUrl + item.icon)
                .error(R.drawable.bg_placeholder)
                .into(ivThumbnail)
            root.setOnClickListener {
                elQuestion.toggle()
                if (elQuestion.isExpanded) {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_bottoms)
                } else {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_rectangle)
                }
            }
            onBindSimulationContentAdapter(holder, item.subbab)
        }
    }

    private fun onBindSimulationContentAdapter(holder: ViewHolder, data : List<Subbab>){
        with(holder.view) {
            val adapter: EReportSubBabAdapter by lazy {
                EReportSubBabAdapter(context)
            }
            rvQuestionPackage.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
            }
            adapter.setData(data)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterLearningAdaptiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLearningAdaptiveBinding) : RecyclerView.ViewHolder(view.root)

}