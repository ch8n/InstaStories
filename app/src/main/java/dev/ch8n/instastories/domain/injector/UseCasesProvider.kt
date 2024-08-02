package dev.ch8n.instastories.domain.injector

import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.domain.usecases.GetViewedIdsUseCase
import dev.ch8n.instastories.domain.usecases.StoryViewedPersistUseCase

object UseCasesProvider {

    val getStoriesRemoteUseCase by lazy {
        GetStoriesRemoteUseCase(
            RepositoryProvider.storyRepository
        )
    }

    val getViewedIdsUseCase by lazy {
        GetViewedIdsUseCase(
            RepositoryProvider.storyRepository
        )
    }

    val storyViewedPersistUseCase by lazy {
        StoryViewedPersistUseCase(
            RepositoryProvider.storyRepository
        )
    }
}