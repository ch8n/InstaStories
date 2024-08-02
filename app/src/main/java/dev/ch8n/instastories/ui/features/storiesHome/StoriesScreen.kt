package dev.ch8n.instastories.ui.features.storiesHome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.ch8n.instastories.data.remote.injector.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.Screen
import dev.ch8n.instastories.ui.features.storiesHome.components.CircularUserIcon
import dev.ch8n.instastories.utils.noRippleClick


@Composable
fun StoriesHomeScreen(navController: NavController) {
    val viewModel = remember { StoryViewModel() }
    val screenState by viewModel.screenState.collectAsState()
    StoriesHomeContent(
        storiesHomeState = screenState,
        navigateToPreview = { story ->
            navController.navigate(Screen.StoriesPreviewScreen(story.id))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoriesHomeContent(
    storiesHomeState: StoriesHomeState,
    navigateToPreview: (story: Story) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (storiesHomeState.error.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(Color.Red)
            ) {
                Text(
                    text = storiesHomeState.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Text(
            text = "InstaStories - Chetan Gupta",
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 8.dp),
            fontStyle = FontStyle.Italic
        )


        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (storiesHomeState.isLoading) {
                items(5) { item ->
                    if (item == 0) Spacer(modifier = Modifier.size(16.dp))
                    CircularUserIcon(
                        userName = "",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(64.dp),
                        isLoading = true
                    )

                    if (item == storiesHomeState.stories.lastIndex) Spacer(
                        modifier = Modifier.size(
                            16.dp
                        )
                    )
                }
            }

            if (!storiesHomeState.isLoading) {
                itemsIndexed(storiesHomeState.stories) { index, story ->
                    if (index == 0) Spacer(modifier = Modifier.size(16.dp))

                    CircularUserIcon(
                        userName = story.userName,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(64.dp)
                            .noRippleClick {
                                navigateToPreview.invoke(story)
                            },
                    )

                    if (index == storiesHomeState.stories.lastIndex) Spacer(
                        modifier = Modifier.size(
                            16.dp
                        )
                    )
                }
            }
        }
    }
}