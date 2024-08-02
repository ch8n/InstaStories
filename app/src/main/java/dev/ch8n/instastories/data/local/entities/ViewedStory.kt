package dev.ch8n.instastories.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ViewedStory(
    @PrimaryKey val id: String
)