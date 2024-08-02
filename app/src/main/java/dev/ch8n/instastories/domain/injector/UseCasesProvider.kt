package dev.ch8n.instastories.domain.injector

import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase

object UseCasesProvider {

    val getStoriesRemoteUseCase by lazy {
        GetStoriesRemoteUseCase(
            RepositoryProvider.storyRepository
        )
    }
}