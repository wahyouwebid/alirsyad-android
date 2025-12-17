package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.data.model.jenjang.Jenjang
import com.alirsyad.app.databinding.AdapterCommonBinding

class JenjangAdapter(
    private val showDetail: (Jenjang) -> Unit
) : RecyclerView.Adapter<JenjangAdapter.ViewHolder>() {

    private var data = ArrayList<Jenjang>()

    fun setData(itemList: List<Jenjang>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvName.text = item.name
            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterCommonBinding) : RecyclerView.ViewHolder(view.root)

}