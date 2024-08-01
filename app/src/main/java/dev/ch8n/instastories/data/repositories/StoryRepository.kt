package dev.ch8n.instastories.data.repositories

import dev.ch8n.instastories.DummyData
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.data.remote.StoriesService

class StoryRepository(private val storiesService: StoriesService) {
    suspend fun fetchStories(): List<Story> {
        // Instead of fetching from API, return dummy data
        return DummyData.getStories()
    }
}