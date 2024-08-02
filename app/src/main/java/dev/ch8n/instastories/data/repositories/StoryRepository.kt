package dev.ch8n.instastories.data.repositories

import dev.ch8n.instastories.FakeStories
import dev.ch8n.instastories.data.remote.StoriesService
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.utils.ResultOf

class StoryRepository(
    private val storiesService: StoriesService,
) {
    suspend fun fetchStories(): ResultOf<List<Story>> {
        return ResultOf.build {
            storiesService.getStories()
        }
    }
}