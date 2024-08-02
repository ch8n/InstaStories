package dev.ch8n.instastories.domain.usecases

import dev.ch8n.instastories.data.local.entities.ViewedStory
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetViewedIdsUseCase(private val repository: StoryRepository) {
    suspend operator fun invoke(): Flow<List<String>> {
        return repository.getAllViewedStories().map {
            it.map { it.id }
        }
    }
}