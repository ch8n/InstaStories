package dev.ch8n.instastories.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ch8n.instastories.data.local.dao.ViewedStoryDao
import dev.ch8n.instastories.data.local.entities.ViewedStory

@Database(entities = [ViewedStory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun viewedStoryDao(): ViewedStoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "story_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}