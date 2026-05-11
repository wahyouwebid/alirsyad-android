package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.model.simulation.Simulasi
import com.alirsyad.app.databinding.AdapterSimulationContentBinding
import com.alirsyad.app.utils.Utils.dateFormat

class SimulationContentAdapter(
    private val showDetail: (Simulasi) -> Unit,
) : RecyclerView.Adapter<SimulationContentAdapter.ViewHolder>() {

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
            tvDate.text = dateFormat(item.createdAt)
            tvRating.text = "${item.rataRataScore}/100"
            rating.rating = item.bintangScore
            ivThumbnail.load(item.icon)
            root.setOnClickListener {
                showDetail(data[position])
            }

        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterSimulationContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterSimulationContentBinding) : RecyclerView.ViewHolder(view.root)

}