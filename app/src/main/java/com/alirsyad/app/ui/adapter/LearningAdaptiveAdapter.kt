package com.alirsyad.app.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.R
import com.alirsyad.app.data.model.adaptive.AdaptiveModule
import com.alirsyad.app.data.model.adaptive.QuestionPackage
import com.alirsyad.app.data.model.params.DataParams
import com.alirsyad.app.databinding.AdapterLearningAdaptiveBinding
import com.alirsyad.app.ui.learning.learningadaptive.level.LevelAdaptiveActivity
import com.alirsyad.app.ui.learning.learningadaptive.question.QuestionAdaptiveActivity
import com.alirsyad.app.utils.Constant
import com.bumptech.glide.Glide

class LearningAdaptiveAdapter(
    private val appContext: Context,
    private val dataParams: DataParams?,
) : RecyclerView.Adapter<LearningAdaptiveAdapter.ViewHolder>() {
    private var context : Context? = null
    private var data = ArrayList<AdaptiveModule>()
    private var dataParam: DataParams? = null

    fun setData(itemList: List<AdaptiveModule>) {
        dataParam = dataParams
        context = appContext
        data.clear()
        data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]
            tvTitle.text = item.name
            Glide.with(ivThumbnail)
                .load(item.icon)
                .into(ivThumbnail)
            root.setOnClickListener {
                elQuestion.toggle()
                if (elQuestion.isExpanded) {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_bottoms)
                } else {
                    ivArrow.background = ContextCompat.getDrawable(root.context, R.drawable.ic_arrow_rectangle)
                }
            }
            onBindSimulationContentAdapter(holder, item.paketSoal)
        }
    }

    private fun onBindSimulationContentAdapter(holder: ViewHolder, data : List<QuestionPackage>){
        with(holder.view) {
            val adapter: LearningAdaptiveSubBabAdapter by lazy {
                LearningAdaptiveSubBabAdapter {simulasi -> detailQuestion(simulasi)}
            }
            rvQuestionPackage.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
            }
            adapter.setData(data)
        }
    }

    private fun detailQuestion(item: QuestionPackage) {
//        context?.startActivity(Intent(context, QuestionAdaptiveActivity::class.java).also {
//            val params = DataParams(
//                item.id,
//                item.judulSubbab,
//                dataParam?.tingkat,
//                dataParam?.jenjang
//            )
//            it.putExtra(Constant.Intent.DATA, params)
//            it.putExtra(Constant.Intent.ID, dataParam?.id)
//            it.putExtra(Constant.Intent.NAME, dataParam?.name)
//        })
        context?.startActivity(Intent(context, LevelAdaptiveActivity::class.java).also {
            val params = DataParams(
                item.id,
                item.judulSubbab,
                dataParam?.tingkat,
                dataParam?.jenjang
            )
            it.putExtra(Constant.Intent.DATA, params)
            it.putExtra(Constant.Intent.ID, dataParam?.id)
            it.putExtra(Constant.Intent.NAME, dataParam?.name)
            it.putExtra(Constant.Intent.MATA_PELAJARAN_ID, item.id)
        })
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterLearningAdaptiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLearningAdaptiveBinding) : RecyclerView.ViewHolder(view.root)

}