package dev.ch8n.instastories.domain.models

import dev.ch8n.instastories.data.local.entities.StoryEntity

data class Story(
    val id: String,
    val imageUrl: String,
    val userName: String
) {
    companion object {
        fun Story.toEntity(): StoryEntity {
            return StoryEntity(
                id = id,
                imageUrl = imageUrl,
                userName = userName
            )
        }
    }
}