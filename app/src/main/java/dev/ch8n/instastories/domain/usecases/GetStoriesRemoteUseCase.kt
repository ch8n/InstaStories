package dev.ch8n.instastories.domain.usecases

import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.data.repositories.StoryRepository

class GetStoriesRemoteUseCase(private val repository: StoryRepository) {
    suspend operator fun invoke(): List<Story> {
        return repository.fetchStories()
    }
}