package com.alirsyad.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alirsyad.app.R
import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.databinding.AdapterQuestionNewBinding
import com.alirsyad.app.utils.Utils.checkIsSpecialChar
import com.alirsyad.app.utils.hide
import com.alirsyad.app.utils.readHtml
import com.alirsyad.app.utils.show
import com.bumptech.glide.Glide


class QuestionAdapterUtilsNew(
    private val updateData: (QuestionEntity) -> Unit
) : RecyclerView.Adapter<QuestionAdapterUtilsNew.ViewHolder>() {

    private var data = ArrayList<QuestionEntity>()
    private var isButtonClicked = false
    private lateinit var binding: AdapterQuestionNewBinding

    fun setData(itemList: List<QuestionEntity>){
//        val diffCallback = QuestionDiffUtils(this.data, itemList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        this.data.clear()
//        this.data.addAll(itemList)
//        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        with(holder.view) {
//            binding = holder.view
//            val item = data[position]
//            if (checkIsSpecialChar(item.question.toString())) {
//                tvTitleMath.show()
//                tvTitle.hide(true)
//                tvTitleMath.text = "${position + 1}. ${item.question?.readHtml()}"
//            } else {
//                tvTitleMath.hide(true)
//                tvTitle.show()
//                tvTitle.text = "${position + 1}. ${item.question?.readHtml()}"
//            }
//
//            if (item.image?.isEmpty() == true) {
//                ivThumbnail.hide(true)
//            } else {
//                ivThumbnail.show()
//                Glide.with(ivThumbnail)
//                    .load(item.image)
//                    .placeholder(R.drawable.bg_placeholder_image)
//                    .error(R.drawable.bg_placeholder_image)
//                    .into(ivThumbnail)
//            }
//
//            dibAnswerA.setTextAnswer(item.answerA)
//            dibAnswerB.setTextAnswer(item.answerB)
//            dibAnswerC.setTextAnswer(item.answerC)
//            dibAnswerD.setTextAnswer(item.answerD)
//            dibAnswerE.setTextAnswer(item.answerE)
//
//            if (isButtonClicked) {
//                when(item.selectedAnswer) {
//                    1 -> dibAnswerA.setChecked(true)
//                    2 -> dibAnswerB.setChecked(true)
//                    3 -> dibAnswerC.setChecked(true)
//                    4 -> dibAnswerD.setChecked(true)
//                    5 -> dibAnswerE.setChecked(true)
//                    else -> {
//                        dibAnswerA.setChecked(false)
//                        dibAnswerB.setChecked(false)
//                        dibAnswerC.setChecked(false)
//                        dibAnswerD.setChecked(false)
//                        dibAnswerE.setChecked(false)
//                    }
//                }
//                if (!item.isAnswered) {
//                    dibAnswerA.setEmptyAnswer()
//                    dibAnswerB.setEmptyAnswer()
//                    dibAnswerC.setEmptyAnswer()
//                    dibAnswerD.setEmptyAnswer()
//                    dibAnswerE.setEmptyAnswer()
//                } else {
//                    dibAnswerA.checkEnable(dibAnswerA.getIsChecked())
//                    dibAnswerB.checkEnable(dibAnswerB.getIsChecked())
//                    dibAnswerC.checkEnable(dibAnswerC.getIsChecked())
//                    dibAnswerD.checkEnable(dibAnswerD.getIsChecked())
//                    dibAnswerE.checkEnable(dibAnswerE.getIsChecked())
//                }
//            }
//
//            dibAnswerA.setOnClickListener {
//                postData(item, 1, item.answerA)
//                dibAnswerA.setChecked(true)
//                dibAnswerB.setChecked(false)
//                dibAnswerC.setChecked(false)
//                dibAnswerD.setChecked(false)
//                dibAnswerE.setChecked(false)
//            }
//
//            dibAnswerB.setOnClickListener {
//                postData(item, 2, item.answerB)
//                dibAnswerA.setChecked(false)
//                dibAnswerB.setChecked(true)
//                dibAnswerC.setChecked(false)
//                dibAnswerD.setChecked(false)
//                dibAnswerE.setChecked(false)
//            }
//
//            dibAnswerC.setOnClickListener {
//                postData(item, 3, item.answerC)
//                dibAnswerA.setChecked(false)
//                dibAnswerB.setChecked(false)
//                dibAnswerC.setChecked(true)
//                dibAnswerD.setChecked(false)
//                dibAnswerE.setChecked(false)
//            }
//
//            dibAnswerD.setOnClickListener {
//                postData(item, 4, item.answerD)
//                dibAnswerA.setChecked(false)
//                dibAnswerB.setChecked(false)
//                dibAnswerC.setChecked(false)
//                dibAnswerD.setChecked(true)
//                dibAnswerE.setChecked(false)
//            }
//
//            dibAnswerE.setOnClickListener {
//                postData(item, 5, item.answerE)
//                dibAnswerA.setChecked(false)
//                dibAnswerB.setChecked(false)
//                dibAnswerC.setChecked(false)
//                dibAnswerD.setChecked(false)
//                dibAnswerE.setChecked(true)
//            }
//        }
    }

    private fun postData(
        item: QuestionEntity,
        position: Int,
        answer: String?
    ) = with(binding) {

        item.isAnswered = true
        item.answered = answer
        item.selectedAnswer = position
        updateData.invoke(QuestionEntity(
            item.id,
            item.question,
            item.image,
            item.answerA,
            item.answerB,
            item.answerC,
            item.answerD,
            item.answerE,
            true,
            answer,
            position
        ))
    }

    fun checkAnswer(isClicked: Boolean) {
        isButtonClicked = isClicked
    }

    fun checkAnswerIsEmpty() = data.filter { !it.isAnswered }

    fun getDataAnswer() = data

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterQuestionNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterQuestionNewBinding) : RecyclerView.ViewHolder(view.root)

}