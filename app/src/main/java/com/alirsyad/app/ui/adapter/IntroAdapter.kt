package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.data.model.intro.DataIntro
import com.alirsyad.app.databinding.AdapterIntroBinding
import com.bumptech.glide.Glide

class IntroAdapter : RecyclerView.Adapter<IntroAdapter.ViewHolder>() {

    private var data = ArrayList<DataIntro>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList: List<DataIntro>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            Glide.with(ivIntro)
                .load(item.image)
                .into(ivIntro)
            tvTitle.text = item.title
            tvDescription.text = item.description
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterIntroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterIntroBinding) : RecyclerView.ViewHolder(view.root)

}