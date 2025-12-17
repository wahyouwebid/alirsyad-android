package com.alirsyad.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.database.dao.QuestionDao
import com.alirsyad.app.data.entity.QuestionEntity
@Database(
    entities = [
        QuestionEntity::class,
    ],
    version = BuildConfig.VERSION_CODE,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    abstract fun questionDao() : QuestionDao

    companion object {

        @Volatile private var instance : RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "dib.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}