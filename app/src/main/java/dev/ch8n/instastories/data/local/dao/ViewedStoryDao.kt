package dev.ch8n.instastories.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ch8n.instastories.data.local.entities.ViewedStory
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewedStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(viewedStory: ViewedStory)

    @Query("SELECT * FROM ViewedStory")
    fun getAllViewedStories(): Flow<List<ViewedStory>>

    @Query("SELECT * FROM ViewedStory WHERE id = :storyId")
    suspend fun getViewedStoryById(storyId: String): ViewedStory?
}