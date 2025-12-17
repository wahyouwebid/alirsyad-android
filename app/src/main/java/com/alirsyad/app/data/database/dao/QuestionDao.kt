package com.alirsyad.app.data.database.dao

import androidx.room.*
import com.alirsyad.app.data.entity.QuestionEntity
import io.reactivex.Single


@Dao
interface QuestionDao {

    @Query("SELECT * FROM question")
    fun getQuestion(): Single<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: List<QuestionEntity>)

    @Update
    suspend fun updateQuestion(data : QuestionEntity)

    @Query("DELETE FROM question")
    suspend fun deleteAllQuestion()
}