package dev.ch8n.instastories.ui.features.storiesHome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.ch8n.instastories.data.remote.config.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.Screen
import dev.ch8n.instastories.ui.features.storiesHome.components.CircularUserIcon
import dev.ch8n.instastories.utils.noRippleClick


@Composable
fun StoriesHomeScreen(navController: NavController) {
    val repository = remember {
        StoryRepository(RemoteServiceProvider.storiesService)
    }
    val fetchUseCase = remember {
        GetStoriesRemoteUseCase(repository)
    }
    val viewModel = remember { StoryViewModel(fetchUseCase) }
    val screenState by viewModel.screenState.collectAsState()
    StoriesHomeContent(
        storiesHomeState = screenState,
        navigateToPreview = { story ->
            //TODO passing argument
            navController.navigate(Screen.StoriesPreview(story.id).route)
        }
    )
}

@Composable
fun StoriesHomeContent(
    storiesHomeState: StoriesHomeState,
    navigateToPreview: (story: Story) -> Unit
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
            itemsIndexed(storiesHomeState.stories) { index, story ->
                if (index == 0) Spacer(modifier = Modifier.size(16.dp))

                CircularUserIcon(
                    userName = story.userName,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(64.dp)
                        .noRippleClick {
                            navigateToPreview.invoke(story)
                        }
                )

                if (index == storiesHomeState.stories.lastIndex) Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}