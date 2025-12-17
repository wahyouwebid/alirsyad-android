package com.alirsyad.app.ui.ereportscore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.data.model.ereport.Nilai
import com.alirsyad.app.data.model.ereport.Subbab
import com.alirsyad.app.databinding.AdapterEreportScoreSubbabBinding

class EReportSubBabAdapter(
    private val appContext: Context?,
) : RecyclerView.Adapter<EReportSubBabAdapter.ViewHolder>() {

    private var context : Context? = null
    private var data = ArrayList<Subbab>()

    fun setData(itemList: List<Subbab>) {
        context = appContext
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.nama
            root.setOnClickListener {
                elSub.toggle()
                if (elSub.isExpanded) {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_bottoms)
                } else {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_rectangle)
                }
            }
            onBindSimulationContentAdapter(holder, item.nilai)
        }
    }

    private fun onBindSimulationContentAdapter(holder: ViewHolder, data : List<Nilai>){
        with(holder.view) {
            val adapter: EReportProgressScoreAdapter by lazy {
                EReportProgressScoreAdapter(context)
            }
            rvScore.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
            }
            adapter.setData(data)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterEreportScoreSubbabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterEreportScoreSubbabBinding) : RecyclerView.ViewHolder(view.root)

}