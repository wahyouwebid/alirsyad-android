package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.model.banner.DataBanner
import com.alirsyad.app.databinding.AdapterHomeCarouselBinding
import com.alirsyad.app.ui.pdfview.PdfViewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var data = ArrayList<DataBanner>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList: List<DataBanner>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            Glide.with(ivBanner)
                .load(BuildConfig.imageUrl + item.image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(ivBanner)
            root.setOnClickListener { view ->
                view.context.startActivity(
                    Intent(
                        view.context,
                        PdfViewActivity::class.java
                    ).also { intent ->
                        intent.putExtra("url", item.file)
                    })
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterHomeCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterHomeCarouselBinding) : RecyclerView.ViewHolder(view.root)

}