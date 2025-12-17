package com.alirsyad.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.data.model.adaptive.QuestionPackage
import com.alirsyad.app.databinding.AdapterQuestionPackageSubbabBinding

class LearningAdaptiveSubBabAdapter(
    private val showDetail: (QuestionPackage) -> Unit
) : RecyclerView.Adapter<LearningAdaptiveSubBabAdapter.ViewHolder>() {

    private var data = ArrayList<QuestionPackage>()

    fun setData(itemList: List<QuestionPackage>) {
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = String.format("Sub Bab ${item.subbab} : ${item.judulSubbab}")
            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterQuestionPackageSubbabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterQuestionPackageSubbabBinding) : RecyclerView.ViewHolder(view.root)

}