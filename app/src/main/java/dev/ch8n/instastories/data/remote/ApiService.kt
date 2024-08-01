package dev.ch8n.instastories.data.remote

import dev.ch8n.instastories.domain.models.Story
import retrofit2.http.GET

interface StoriesService {
    @GET("stories")
    suspend fun getStories(): List<Story>
}

