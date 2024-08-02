package dev.ch8n.instastories.domain.injector

import dev.ch8n.instastories.data.remote.injector.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository

object RepositoryProvider {

    val storyRepository by lazy {
        StoryRepository(
            RemoteServiceProvider.storiesService,
        )
    }
}