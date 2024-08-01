package dev.ch8n.instastories

import dev.ch8n.instastories.domain.models.Story

object DummyData {
    fun getStories(): List<Story> {
        return listOf(
            Story(
                id = "1",
                imageUrl = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
                userName = "john doe"
            ),
            Story(
                id = "2",
                imageUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
                userName = "jane smith"
            ),
            Story(
                id = "3",
                imageUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d",
                userName = "alice wonder"
            ),
            Story(
                id = "4",
                imageUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9",
                userName = "bob builder"
            ),
            Story(
                id = "5",
                imageUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12",
                userName = "charlie brown"
            ),
            Story(
                id = "6",
                imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
                userName = "dana scully"
            ),
            Story(
                id = "7",
                imageUrl = "https://images.unsplash.com/photo-1519345182560-3f2917c472ef",
                userName = "elon musk"
            ),
            Story(
                id = "8",
                imageUrl = "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e",
                userName = "frank underwood"
            ),
            Story(
                id = "9",
                imageUrl = "https://images.unsplash.com/photo-1511367461989-f85a21fda167",
                userName = "george clooney"
            ),
            Story(
                id = "10",
                imageUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2",
                userName = "hannah montana"
            )
        )
    }
}

