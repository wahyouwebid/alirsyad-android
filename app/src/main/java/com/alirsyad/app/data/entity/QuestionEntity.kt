package com.alirsyad.app.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "question")
@Parcelize
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val question: String?,
    val image: String?,
    val answerA: String?,
    val answerB: String?,
    val answerC: String?,
    val answerD: String?,
    val answerE: String?,
    var isAnswered: Boolean = false,
    var answered: String?,
    var selectedAnswer: Int
): Parcelable