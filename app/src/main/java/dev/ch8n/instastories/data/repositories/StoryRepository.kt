package dev.ch8n.instastories.data.repositories

import dev.ch8n.instastories.DummyData
import dev.ch8n.instastories.data.local.dao.ViewedStoryDao
import dev.ch8n.instastories.data.local.entities.ViewedStory
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.data.remote.StoriesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class StoryRepository(
    private val storiesService: StoriesService,
    private val viewedStoryDao: ViewedStoryDao
) {
    suspend fun fetchStories(): List<Story> {
        // Instead of fetching from API, return dummy data
        return DummyData.getStories()
    }

    suspend fun insert(viewedStory: ViewedStory) {
        withContext(Dispatchers.IO) {
            viewedStoryDao.insert(viewedStory)
        }
    }

    suspend fun getAllViewedStories(): Flow<List<ViewedStory>> {
        return viewedStoryDao
            .getAllViewedStories()
            .flowOn(Dispatchers.IO)
    }

    suspend fun getViewedStoryById(storyId: String): ViewedStory? {
        return withContext(Dispatchers.IO) {
            viewedStoryDao.getViewedStoryById(storyId)
        }
    }
}