package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.update.DataUpdate
import com.alirsyad.app.databinding.AdapterNewUpdateGridBinding
import com.alirsyad.app.utils.Constant
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.show
import com.bumptech.glide.Glide

class NewUpdateGridAdapter(
    private val detailModule: (Int, String, Int, String, String) -> Unit,
): RecyclerView.Adapter<NewUpdateGridAdapter.ViewHolder>() {

    private var data = ArrayList<DataUpdate>()

    fun setData(itemList: List<DataUpdate>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvModule.text = item.triggerName
            Glide.with(ivModule)
                .load(item.logo)
                .error(R.drawable.ic_subject_module)
                .fitCenter()
                .into(ivModule)
            if (item.trigger == Constant.TRIGGER_VIDEO) {
                ivPlay.show()
                view.show()
            } else {
                ivPlay.hide(true)
                view.hide(true)
            }
            root.setOnClickListener {
                detailModule(
                    item.triggerRel?.mataPelajaranId ?: -1,
                    item.mataPelajaran,
                    item.triggerRel?.id ?: -1,
                    item.triggerName,
                    item.trigger ?: ""
                )
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterNewUpdateGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterNewUpdateGridBinding) : RecyclerView.ViewHolder(view.root)

}