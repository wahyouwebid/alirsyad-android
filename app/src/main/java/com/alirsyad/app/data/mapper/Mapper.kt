package com.alirsyad.app.data.mapper

import com.alirsyad.app.data.entity.QuestionEntity
import com.alirsyad.app.data.model.adaptive.Question

object Mapper {

    fun mapResponsesToEntities(input: List<Question>): List<QuestionEntity> {
        val data = ArrayList<QuestionEntity>()
        input.map {
            val question = QuestionEntity(
                id = it.id,
                question = it.soal,
                image = it.image,
                answerA = it.jawaban?.get(0),
                answerB = it.jawaban?.get(1),
                answerC = it.jawaban?.get(2),
                answerD = it.jawaban?.get(3),
                answerE = it.jawaban?.get(4),
                answered = "",
                isAnswered = false,
                selectedAnswer = -1
            )
            data.add(question)
        }
        return data
    }
}