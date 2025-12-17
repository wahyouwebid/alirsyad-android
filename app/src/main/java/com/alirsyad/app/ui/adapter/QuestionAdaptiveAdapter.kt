package com.alirsyad.app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirsyad.app.data.model.adaptive.Question
import com.alirsyad.app.databinding.AdapterQuestionNewBinding

class QuestionAdaptiveAdapter(
    private val updateData: (Question) -> Unit
) : RecyclerView.Adapter<QuestionAdaptiveAdapter.ViewHolder>() {

    private val data = ArrayList<Question>()
    private var highlightUnanswered = false

    fun setData(itemList: List<Question>) {
        val diffCallback = QuestionDiffUtils(data, itemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(itemList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setHighlightUnanswered(shouldHighlight: Boolean) {
        highlightUnanswered = shouldHighlight
        notifyDataSetChanged()
    }

    fun getUnanswered(): List<Question> {
        return data.filter { it.isAnswered == null }
    }

    fun getAnswered(): List<Question> {
        return data.filter { it.isAnswered != null }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterQuestionNewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val binding = holder.binding

        // Soal
        if (!item.soal.isNullOrBlank() && item.soal.contains("<math")) {
            binding.tvTitle.visibility = View.GONE
            binding.tvTitleMath.visibility = View.INVISIBLE
            binding.tvTitleMath.post {
                binding.tvTitleMath.setText(item.soal)
                binding.tvTitleMath.visibility = View.VISIBLE
            }
        } else {
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvTitleMath.visibility = View.GONE
            binding.tvTitle.text = android.text.Html.fromHtml(item.soal ?: "", android.text.Html.FROM_HTML_MODE_LEGACY)
        }

        // Image
        binding.ivThumbnail.apply {
            visibility = if (!item.image.isNullOrBlank()) View.VISIBLE else View.GONE
            item.image?.let { load(it) }
        }

        // Radio Buttons
        val radioButtons = listOf(
            binding.dibAnswerA,
            binding.dibAnswerB,
            binding.dibAnswerC,
            binding.dibAnswerD,
            binding.dibAnswerE
        )

        radioButtons.forEach {
            it.visibility = View.GONE
            it.setChecked(false)
            it.setErrorState(false) // Tambahkan method ini di `DibRadioButton` untuk support warna merah
            binding.tvError.visibility = View.GONE
        }

        item.jawaban?.forEachIndexed { index, option ->
            if (index < radioButtons.size) {
                val radio = radioButtons[index]
                radio.visibility = View.VISIBLE
                radio.setTitle(option)

                // Is checked
                val isSelected = item.isAnswered != null && item.isAnswered == index
                radio.setChecked(isSelected)

                // Jika highlight aktif dan belum dijawab
                if (highlightUnanswered && item.isAnswered == null) {
                    radio.setErrorState(true)
                    binding.tvError.visibility = View.VISIBLE
                }

                radio.setOnClickListener {
                    item.isAnswered = index
                    item.answer = option
                    item.idAnswer = index
                    updateData(item)

                    radioButtons.forEachIndexed { i, rb ->
                        binding.tvError.visibility = View.GONE
                        rb.setChecked(i == index)
                    }
                }
            }
        }
    }

    class ViewHolder(val binding: AdapterQuestionNewBinding) : RecyclerView.ViewHolder(binding.root)
}
