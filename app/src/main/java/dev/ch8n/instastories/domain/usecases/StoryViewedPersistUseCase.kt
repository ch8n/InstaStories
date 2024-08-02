package dev.ch8n.instastories.domain.usecases

import dev.ch8n.instastories.data.local.entities.ViewedStory
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story


class StoryViewedPersistUseCase(private val repository: StoryRepository) {
    suspend operator fun invoke(story: Story) {
        repository.insert(ViewedStory(story.id))
    }
}