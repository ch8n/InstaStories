package dev.ch8n.instastories.ui.features.storiesPreview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import dev.ch8n.instastories.data.remote.config.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.Screen
import dev.ch8n.instastories.ui.features.storiesPreview.component.AutoScrollPagerIndicator
import dev.ch8n.instastories.ui.features.storiesPreview.component.AutoScrollingPager
import dev.ch8n.instastories.ui.features.storiesPreview.component.CrossIcon


@Composable
fun StoriesPreviewScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    val arguments = backStackEntry.toRoute<Screen.StoriesPreviewScreen>()
    val storyId = arguments.storyId
    val repository = remember {
        StoryRepository(RemoteServiceProvider.storiesService)
    }
    val fetchUseCase = remember {
        GetStoriesRemoteUseCase(repository)
    }
    val viewModel = remember { StoryPreviewViewModelViewModel(fetchUseCase) }
    val screenState by viewModel.screenState.collectAsState()
    StoriesPreviewContent(
        screenState,
        onBackClicked = {
            navController.popBackStack()
        },
        storyId = storyId
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoriesPreviewContent(
    storiesHomeState: StoriesPreviewHomeState,
    onBackClicked: () -> Unit,
    storyId: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (storiesHomeState.stories.isNotEmpty()) {

            val initPageStart = remember {
                val foundIndex = storiesHomeState.stories.indexOfFirst {
                    it.id == storyId
                }
                maxOf(foundIndex, 0)
            }

            val pagerState = rememberPagerState(
                initialPage = initPageStart,
                pageCount = { storiesHomeState.stories.size }
            )

            AutoScrollingPager(
                modifier = Modifier.fillMaxSize(),
                pagerState = pagerState
            ) { pageNumber ->

                val selectedStory = storiesHomeState.stories.get(pageNumber)

                AsyncImage(
                    model = selectedStory.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.8f),
                                Color.Transparent,
                                Color.Transparent,
                            )
                        )
                    )
            ) {
                AutoScrollPagerIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    pagerState = pagerState,
                    indicatorWidth = 45.dp,
                    indicatorCount = storiesHomeState.stories.size
                )

                CrossIcon(
                    modifier = Modifier
                        .size(42.dp)
                        .padding(8.dp)
                        .align(Alignment.End),
                    onClick = onBackClicked,
                )
            }
        }
    }
}