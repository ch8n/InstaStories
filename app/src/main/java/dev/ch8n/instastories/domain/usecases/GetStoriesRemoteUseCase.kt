package dev.ch8n.instastories.domain.usecases

import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.utils.ResultOf

class GetStoriesRemoteUseCase(private val repository: StoryRepository) {
    suspend operator fun invoke(): ResultOf<List<Story>> {
        return repository.fetchStories()
    }
}