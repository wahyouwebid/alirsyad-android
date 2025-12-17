package com.alirsyad.app.ui.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.alirsyad.app.data.model.adaptive.Question


class QuestionDiffUtils(oldQuestion: List<Question>, newQuestion: List<Question>) :
    DiffUtil.Callback() {

    private var data : List<Question> = arrayListOf()
    private var dataOld : List<Question> = arrayListOf()

    init {
        dataOld = oldQuestion
        data = newQuestion
    }

    override fun getOldListSize(): Int {
        return dataOld.size
    }

    override fun getNewListSize(): Int {
        return data.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dataOld[oldItemPosition].id == data[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val dataOld : Question = dataOld[oldItemPosition]
        val data : Question = data[newItemPosition]

        return dataOld.soal.equals(data.soal)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}