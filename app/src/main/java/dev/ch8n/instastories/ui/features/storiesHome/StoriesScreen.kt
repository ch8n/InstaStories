package dev.ch8n.instastories.ui.features.storiesHome

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ch8n.instastories.data.remote.config.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.storiesHome.components.CircularUserIcon


@Preview
@Composable
private fun StoriesScreenPreview() {
    val repository = remember {
        StoryRepository(RemoteServiceProvider.storiesService)
    }
    val fetchUseCase = remember {
        GetStoriesRemoteUseCase(repository)
    }
    val viewModel = remember { StoryViewModel(fetchUseCase) }
    val screenState by viewModel.screenState.collectAsState()
    StoriesScreen(screenState)
}

@Composable
fun StoriesScreen(
    storiesHomeState: StoriesHomeState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "InstaStories - Chetan Gupta",
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 8.dp),
            fontStyle = FontStyle.Italic
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(storiesHomeState.stories) { story ->
                CircularUserIcon(
                    userName = story.userName,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(64.dp)
                )
            }
        }
    }
}